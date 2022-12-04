window.onload = function () {
    init();
}

/**
 * 초기화 함수
 */
function init() {
    const form = document.querySelector("#modelValues");
    /** @type {Array<HTMLInputElement>} */
    const inputs = [...form.querySelectorAll('input[name]')];
    const values = inputs.reduce((res, {name, value}) => ({...res, [name]: value}), {})

    const app = new ChattingApplication({rooms: "#rooms", users: "#users"});
    app.connect(values);
    app.addEventListener();
}

const EVENT_TYPE = {
    USER: {
        LIST: "OldUser".toUpperCase(),
        JOIN: "NewUser".toUpperCase(),
        EXIT: "UserOut".toUpperCase(),
    },
    ROOM: {
        LIST: "OldRoom".toUpperCase(),
        JOIN: "NewRoom".toUpperCase(),
        EXIT: "ExitRoom".toUpperCase(),
    }
}

class ChattingApplication {
    /** @type {ChattingApplication} */
    self;
    /** @type {String} */
    id;
    /** @type {WebSocket} */
    websocket;

    rooms;
    users;

    /**
     *
     * @param {String} rooms
     * @param {String} users
     */
    constructor({rooms, users}) {
        self = this;

        this.rooms = this.optionMaker(rooms);
        this.users = this.optionMaker(users);
    }


    /**
     * 소켓 연결
     *
     * @param {{
     *     id: string;
     *     port: int;
     *     ip: string;
     * }} values
     */
    connect(values) {
        const queryString = this.getQueryString(values);
        this.websocket = new WebSocket("ws://localhost/chat" + queryString);
    }

    /**
     * 소켓 연결
     *
     * @param {{
     *     id: string;
     *     port: int;
     *     ip: string;
     * }} values
     *
     * @return {string}
     */
    getQueryString(values) {
        return "?" + Object.entries(values).map(([key, value]) => key + '=' + value).join("&")
    }

    addEventListener() {
        this.websocket.onopen = this.onopen;
        this.websocket.onclose = this.onclose;
        this.websocket.onmessage = this.onmessage;
    }

    onopen(payload) {
        console.log('connected', payload)
    }

    onclose(payload) {
        console.log('disConnected', payload)
    }

    onmessage(payload) {
        let [protocol, message] = payload.data.split("/");
        protocol = protocol.toUpperCase();

        if (protocol.includes("USER")) {
            self.userEventListener(protocol, message);
        } else if (protocol.includes("ROOM")) {
            self.roomEventListener(protocol, message);
        } else {
            console.error("존재하지 않는 커맨드명");
        }
    }


    roomEventListener(protocol, message) {
        switch (protocol) {
            case EVENT_TYPE.ROOM.LIST:
            case EVENT_TYPE.ROOM.JOIN:
                this.rooms.add(message);
                break;
            case EVENT_TYPE.ROOM.EXIT:
                this.rooms.remove(message);
                break;
            default:
                console.error("roomEventListener 존재하지 않는 커맨드명");
                break;
        }
    }

    userEventListener(protocol, message) {
        switch (protocol) {
            case EVENT_TYPE.USER.LIST:
            case EVENT_TYPE.USER.JOIN:
                this.users.add(message);
                break;
            case EVENT_TYPE.USER.EXIT:
                this.users.remove(message);
                break;
            default:
                console.error("userEventListener 존재하지 않는 커맨드명");
                break;
        }
    }


    optionMaker(id) {
        const element = document.querySelector(id)

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
            }
        }
    }
}



