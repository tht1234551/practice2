package com.tourbest.erp.excel;

import com.lannstark.DefaultContentsStyle;
import com.lannstark.DefaultHeaderStyle;
import com.lannstark.ExcelColumn;
import com.lannstark.ExcelColumnStyle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@DefaultHeaderStyle(
        style = @ExcelColumnStyle(excelCellStyleClass = ExcelCellStyleEnums.class, enumName = "RED")
)
@DefaultContentsStyle(
        style = @ExcelColumnStyle(excelCellStyleClass = ExcelCellStyleEnums.class, enumName = "BLUE")
)
public class TodoVo {

    public TodoVo() {}
    
    @ExcelColumn(headerName = "아이디",
            contentsStyle = @ExcelColumnStyle(excelCellStyleClass = ExcelCellStyleEnums.class, enumName = "BLUE_DOUBLE")
    )
    private String userId;

    @ExcelColumn(headerName = "no",
            contentsStyle = @ExcelColumnStyle(excelCellStyleClass = ExcelCellStyleEnums.class, enumName = "BLUE_LEFT")
    )
    private int id;

    @ExcelColumn(headerName = "타이틀")
    private String title;

    @ExcelColumn(headerName = "완료여부",
            contentsStyle = @ExcelColumnStyle(excelCellStyleClass = ExcelCellStyleEnums.class, enumName = "GREEN")
    )
    private String completed;

}
