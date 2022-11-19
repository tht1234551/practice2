package com.tourbest.erp.excel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lannstark.excel.ExcelFile;
import com.lannstark.excel.multiplesheet.MultiSheetExcelFile;
import com.lannstark.excel.onesheet.OneSheetExcelFile;
import org.apache.poi.ss.SpreadsheetVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
@RequestMapping("/excel")
public class ExcelTestController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    String EXCEL_MIMETYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    @RequestMapping(value = "/view")
    public String view() {
        return null;
    }

    @RequestMapping(value = "/download")
    public void moduleTest(HttpServletResponse response) throws IOException {
        response.setContentType(EXCEL_MIMETYPE);

        WebClient client = WebClient
                .builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build();

        String json = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("todos")
                        .build())
                .retrieve()
                .bodyToMono(String.class).block();

        List<TodoVo> list = Collections.emptyList();

        try {
            ObjectMapper mapper = new ObjectMapper();

            list = mapper.readValue(
                    json,
                    new TypeReference<List<TodoVo>>() {
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        ExcelFile excelFile = new OneSheetExcelFile<>(list, TodoVo.class);

        excelFile.write(response.getOutputStream());
    }

    @RequestMapping(value = "/download2")
    public void moduleTest2(HttpServletResponse response) throws IOException {
        response.setContentType(EXCEL_MIMETYPE);


        WebClient client = WebClient
                .builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build();

        logger.info("start httpRequest");
        String json = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("todos")
                        .build())
                .retrieve()
                .bodyToMono(String.class).block();
        logger.info("end httpRequest");

        List<TodoVo> list = Collections.emptyList();

        try {
            logger.info("start list making");

            ObjectMapper mapper = new ObjectMapper();

            list = mapper.readValue(
                    json,
                    new TypeReference<List<TodoVo>>() {
                    }
            );

            List<TodoVo> temp = new ArrayList<>(list);
            Collections.copy(temp, list);

            int nowRow = 1;
            int maxRows = SpreadsheetVersion.EXCEL2007.getMaxRows();

            while (temp.size() + nowRow < maxRows) {
                nowRow += temp.size();
                int idx = nowRow;

                List<TodoVo> addList = temp.stream().map((it) ->
                    TodoVo.builder()
                            .id(it.getId() + idx - 1)
                            .userId(it.getUserId())
                            .title(it.getTitle())
                            .completed(it.getCompleted())
                            .build()
                ).collect(Collectors.toList());

                list.addAll(addList);
            }

            logger.info("end list making");
            logger.info("row: " + nowRow);
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("start excel making");
        ExcelFile excelFile = new MultiSheetExcelFile(list, TodoVo.class) {
            @Override
            public void write(OutputStream stream) throws IOException {
                logger.info("end excel making");

                logger.info("start sending");
                wb.write(stream);
                wb.close();
                wb.dispose();
                stream.close();
                logger.info("end sending");
            }
        };

        excelFile.write(response.getOutputStream());
    }
}
