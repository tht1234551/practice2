window.onload = function () {
    init();
}

/**
 * 초기화 함수
 */
function init() {
    const params = getModelValues("#modelValues")

    const app = new ChattingApplication({
        params: params,
        rooms: "#rooms",
        users: "#users",
        msg: "#msg",
        sendMsg : "#sendMsg",
        sendMessage: "#sendMessage",
        joinRoom: "#joinRoom",
        makeRoom: "#makeRoom",
        sendChatting: "#sendChatting",
        sendFile: "#sendFile"
    });

    app.connect();
}

/**
 * jsp 에서 해당 엘리먼트의 하위 input 의 name, value 를 각각 키와 값으로 반환함
 *
 * @param {String} id
 * @returns {Object}
 */
function getModelValues(id) {
    const form = document.querySelector("#modelValues");
    /** @type {Array<HTMLInputElement>} */
    const inputs = [...form.querySelectorAll('input[name]')];
    return inputs.reduce((res, {name, value}) => ({...res, [name]: value}), {});
}

const EVENT_TYPE = {
    USER: {
        LIST: "OldUser".toUpperCase(),
        JOIN: "NewUser".toUpperCase(),
        EXIT: "UserOut".toUpperCase(),
    }, ROOM: {
        LIST: "OldRoom".toUpperCase(),
        JOIN: "NewRoom".toUpperCase(),
        EXIT: "ExitRoom".toUpperCase(),
        CREATE: "CreateRoom".toUpperCase()
    }
}

/**
 *
 * @param {Object} params
 * @param {String} rooms
 * @param {String} users
 * @param {String} sendMessage
 * @param {String[]} buttons
 */
function ChattingApplication({
                                 params,
                                 rooms,
                                 users,
                                 msg,
                                 sendMsg,
                                 ...buttons
                             }) {
    const self = this;

    this.init = () => {
        this.params = params;
        this.rooms = self.optionMaker(rooms);
        this.users = self.optionMaker(users);
        this.msg = msg;
        this.sendMsg = sendMsg;
        this.buttons = buttons;
    }

    /**
     * 소켓 연결
     */
    this.connect = () => {
        const queryString = this.getQueryString(this.params);
        this.websocket = new WebSocket("ws://localhost/chat" + queryString);
        this.addWebSocketEventListener();
        this.addEventListener();
        console.log(this.websocket)
    }

    /**
     * 객체를 쿼리스트링으로 변환
     *
     * @return {string}
     */
    this.getQueryString = (values) => {
        return "?" + Object.entries(values).map(([key, value]) => key + '=' + value).join("&")
    }

    this.addWebSocketEventListener = () => {
        this.websocket.onopen = this.onopen;
        this.websocket.onclose = this.onclose;
        this.websocket.onmessage = this.onmessage;
    }

    this.addEventListener = () => {
        const events = {
            sendMessage(e) {
                console.log("sendMessage");
            },
            joinRoom(e) {
                const msgElement = document.querySelector(self.msg);
                msgElement.value = '';

                const selectRoom = self.rooms.getSelected();
                if (!selectRoom) return;
                if(self.roomName === selectRoom) {
                    alert("현재 채팅방입니다")
                    return;
                }

                if(self.roomName) {
                    self.websocket.send("ExitRoom/" + self.roomName);
                }

                self.websocket.send("JoinRoom/" + selectRoom);
                self.roomName = selectRoom;
            },
            makeRoom(e) {
                const msgElement = document.querySelector(self.msg);
                msgElement.value = '';

                const roomName = prompt("방이름");

                if (self.roomName) {
                    self.websocket.send("ExitRoom/" + self.roomName);
                }

                self.websocket.send("CreateRoom/" + roomName)
                self.roomName = roomName;
            },
            /**
             * 채팅 전송을 누를때
             *
             * @param {Event} e
             */
            sendChatting(e) {
                e.preventDefault();
                console.log("sendChatting");
                const msgElement = document.querySelector(self.sendMsg);
                const msg = msgElement.value;
                if(!msg) return;
                self.websocket.send(`Chatting/${self.roomName}/${msg}`);
                msgElement.value = '';
            },
            /**
             * 파일 전송을 누를때
             * 
             * @param {Event} e
             */
            sendFile(e) {
                e.preventDefault();
                console.log("sendFile");
            },
        }


        Object.entries(this.buttons).forEach(([key, value]) => {
            /** @type {HTMLButtonElement} */
            const button = document.querySelector(value);
            if (!button) return;
            const fn = events[key];
            if (!fn) return;
            button.addEventListener('click', fn);
        })
    }

    this.onopen = (payload) => {
        console.log('connected', payload)
    }

    this.onclose = (payload) => {
        console.log('disConnected', payload)
    }

    /**
     *
     * @param {MessageEvent} payload
     */
    this.onmessage = (payload) => {
        const data = payload.data;

        const splitIndex = data.indexOf("/")
        let protocol = data.slice(0, splitIndex);
        const message = data.slice(splitIndex + 1);
        // let [protocol, message] = payload.data.split("/");
        protocol = protocol.toUpperCase();

        try {
            if (protocol.includes("USER")) {
                self.userEventListener(protocol, message);
            } else if (protocol.includes("ROOM")) {
                self.roomEventListener(protocol, message);
            } else if (protocol.includes("CHATTING")) {
                self.chattingEventListener(protocol, message);
            } else if (protocol.includes("NOTE")) {
                self.noteEventListener(protocol, message);
            } else {
                throw new Error();
            }
        } catch (e) {
            console.error("존재하지 않는 커맨드명 :" + payload.data, e);
        }
    }


    this.roomEventListener = (protocol, message) => {
        switch (protocol) {
            case EVENT_TYPE.ROOM.LIST:
            case EVENT_TYPE.ROOM.JOIN:
                this.rooms.add(message);
                break;
            case EVENT_TYPE.ROOM.EXIT:
                this.rooms.remove(message);
                break;
            case EVENT_TYPE.ROOM.CREATE:
                // self.websocket.send("JoinRoom/" + message);
                break
            default:
                throw new Error("roomEventListener 존재하지 않는 커맨드명");
        }
    }

    this.userEventListener = (protocol, message) => {
        switch (protocol) {
            case EVENT_TYPE.USER.LIST:
            case EVENT_TYPE.USER.JOIN:
                this.users.add(message);
                break;
            case EVENT_TYPE.USER.EXIT:
                this.users.remove(message);
                break;
            default:
                throw new Error("userEventListener 존재하지 않는 커맨드명");
        }
    }

    /**
     *
     * @param protocol
     * @param {String} message
     */
    this.chattingEventListener = (protocol, message) => {
        const textarea = document.querySelector(this.msg);
        console.log(message.replace("/", " : "))
        textarea.value += message.replace("/", " : ") + "\n";
    }

    this.noteEventListener = (protocol, message) => {
        alert(message.replace("/", " : "));
    }


    this.optionMaker = (id) => {
        const element = document.querySelector(id);

        return {
            add(message) {
                const option = document.createElement('option');
                option.text = message;
                element.append(option);
            },
            remove(message) {
                const arr = [...element.children];
                const findOne = arr.filter(x => x.text === message);

                if (findOne.length) {
                    findOne[0].remove();
                }
            }, getSelected() {
                return element.value;
            }
        }
    }

    this.init();
}


