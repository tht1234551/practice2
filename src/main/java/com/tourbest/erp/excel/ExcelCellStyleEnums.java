package com.tourbest.erp.excel;

import com.lannstark.style.ExcelCellStyle;
import com.lannstark.style.align.DefaultExcelAlign;
import com.lannstark.style.align.ExcelAlign;
import com.lannstark.style.border.DefaultExcelBorders;
import com.lannstark.style.border.ExcelBorderStyle;
import com.lannstark.style.color.DefaultExcelColor;
import com.lannstark.style.color.ExcelColor;
import org.apache.poi.ss.usermodel.CellStyle;


public enum ExcelCellStyleEnums implements ExcelCellStyle {

    RED(DefaultExcelColor.rgb(255,111,111),
            DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN), DefaultExcelAlign.CENTER_CENTER),
    BLUE(DefaultExcelColor.rgb(80,188,233),
            DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN), DefaultExcelAlign.CENTER_CENTER),
    GREEN(DefaultExcelColor.rgb(111,255,124),
            DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN), DefaultExcelAlign.CENTER_CENTER),
    BLUE_LEFT(DefaultExcelColor.rgb(80,188,233),
            DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN), DefaultExcelAlign.RIGHT_CENTER),
    BLUE_DOUBLE(DefaultExcelColor.rgb(80,188,233),
            DefaultExcelBorders.newInstance(ExcelBorderStyle.DOUBLE), DefaultExcelAlign.LEFT_CENTER);

    private final ExcelColor backgroundColor;
    private final DefaultExcelBorders borders;
    private final ExcelAlign align;

    ExcelCellStyleEnums(ExcelColor backgroundColor, DefaultExcelBorders borders, ExcelAlign align) {
//        new Color(0xFF6F6F);
        this.backgroundColor = backgroundColor;
        this.borders = borders;
        this.align = align;
    }

    @Override
    public void apply(CellStyle cellStyle) {
        backgroundColor.applyForeground(cellStyle);
        borders.apply(cellStyle);
        align.apply(cellStyle);
    }

}