package sh.ricky.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * @author SHI
 * @POI工具类
 */
public class POIUtil {
    private static DataFormatter FMT = new DataFormatter();

    /**
     * 从Excel文件获取工作表对象
     * 
     * @param path 文件路径
     * @param load 是否加载全部资源
     * @return
     * @throws EncryptedDocumentException
     * @throws InvalidFormatException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static Workbook getWorkbook(String path, boolean load) throws EncryptedDocumentException, InvalidFormatException, FileNotFoundException, IOException {
        if (load) {
            return WorkbookFactory.create(new FileInputStream(path));
        } else {
            return WorkbookFactory.create(new File(path));
        }
    }

    /**
     * 根据页索引获取指定工作表中的标签页对象
     * 
     * @param wb
     * @param idx
     * @return
     */
    public static Sheet getSheetAt(Workbook wb, int idx) {
        Sheet st = wb.getSheetAt(idx - 1);
        return st == null ? wb.createSheet() : st;
    }

    /**
     * 根据行索引获取指定标签页中的行对象
     * 
     * @param st
     * @param idx
     * @return
     */
    public static Row getRowAt(Sheet st, int idx) {
        Row rw = st.getRow(idx - 1);
        return rw == null ? st.createRow(idx - 1) : rw;
    }

    /**
     * 根据页、行索引获取指定工作表中的行对象
     * 
     * @param wb
     * @param sidx
     * @param ridx
     * @return
     */
    public static Row getRowAt(Workbook wb, int sidx, int ridx) {
        return POIUtil.getRowAt(POIUtil.getSheetAt(wb, sidx), ridx);
    }

    /**
     * 根据列索引获取指定行中的单元格对象
     * 
     * @param rw
     * @param idx
     * @return
     */
    public static Cell getCellAt(Row rw, int idx) {
        Cell cl = rw.getCell(idx - 1);
        return cl == null ? rw.createCell(idx - 1) : cl;
    }

    /**
     * 根据行、列索引获取指定标签页中的单元格对象
     * 
     * @param st
     * @param ridx
     * @param cidx
     * @return
     */
    public static Cell getCellAt(Sheet st, int ridx, int cidx) {
        return POIUtil.getCellAt(POIUtil.getRowAt(st, ridx), cidx);
    }

    /**
     * 根据页、行、列索引获取指定工作表中的单元格对象
     * 
     * @param wb
     * @param sidx
     * @param ridx
     * @param cidx
     * @return
     */
    public static Cell getCellAt(Workbook wb, int sidx, int ridx, int cidx) {
        return POIUtil.getCellAt(POIUtil.getRowAt(POIUtil.getSheetAt(wb, sidx), ridx), cidx);
    }

    /**
     * 从单元格获取数据
     * 
     * @param cl
     * @return
     */
    public static Object read(Cell cl) {
        switch (cl.getCellType()) {
        case Cell.CELL_TYPE_STRING:
            return cl.getRichStringCellValue().getString();
        case Cell.CELL_TYPE_NUMERIC:
            if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cl)) {
                return cl.getDateCellValue();
            } else {
                return cl.getNumericCellValue();
            }
        case Cell.CELL_TYPE_BOOLEAN:
            return cl.getBooleanCellValue();
        case Cell.CELL_TYPE_FORMULA:
            return cl.getCellFormula();
        default:
            return null;
        }
    }

    /**
     * 强制从单元格获取String类型数据
     * 
     * @param cl
     * @return
     */
    public static String readString(Cell cl) {
        return POIUtil.FMT.formatCellValue(cl);
    }

    /**
     * 强制从单元格获取Double类型数据
     * 
     * @param cl
     * @return
     */
    public static Double readDouble(Cell cl) {
        // 如果单元格为空，直接返回null值
        if (cl.getCellType() == Cell.CELL_TYPE_BLANK) {
            return null;
        }

        cl.setCellType(Cell.CELL_TYPE_NUMERIC);
        return cl.getNumericCellValue();
    }

    /**
     * 强制从单元格获取Integer类型数据
     * 
     * @param cl
     * @return
     */
    public static Integer readInteger(Cell cl) {
        String v = StringUtils.trimToEmpty(POIUtil.readString(cl));
        return StringUtils.isBlank(v) ? null : Integer.valueOf(v);
    }

    /**
     * 强制从单元格获取BigDecimal类型数据
     * 
     * @param cl
     * @return
     */
    public static BigDecimal readBigDecimal(Cell cl) {
        Double v = POIUtil.readDouble(cl);

        DecimalFormat df = new DecimalFormat();
        df.setGroupingUsed(false);

        return v == null ? null : new BigDecimal(df.format(v));
    }
}
