import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

public class excelpro {

    public static void main(String[] args) throws IOException
    {
        String filePath = "RBB.xls";
        FileInputStream stream = new FileInputStream(filePath);
        HSSFWorkbook workbook = new HSSFWorkbook(stream);//读取现有的Excel
        HSSFSheet sheet= workbook.getSheet("Test1");//得到指定名称的Sheet

        for (Row row : sheet)//第一步读取单元格并输出
        {
            for (Cell cell : row)
            {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }

        HSSFCellStyle style=workbook.createCellStyle();//第二步设置单元格格式，设置保留2位小数--使用Excel内嵌的格式
        style=workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        int rows=sheet.getLastRowNum();

        CellRangeAddress region=new CellRangeAddress(1, rows-1, 0, 0);//第三步合并单元格
        sheet.addMergedRegion(region);
        region=new CellRangeAddress(rows, rows, 0, 3);
        sheet.addMergedRegion(region);

        style.setAlignment(HorizontalAlignment.CENTER);//第四步使所有文本都水平、垂直居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        for(int i=0;i<rows+1;i++){
            for(int j=0;j<5;j++){
                sheet.getRow(i).getCell(j).setCellStyle(style);
            }
        }

        for(int i=1;i<rows;i++){//第五步给计算的单元格设置公式
            HSSFCell n = sheet.getRow(i).getCell(2);
            HSSFCell m = sheet.getRow(i).getCell(3);
            double n1 = (double) n.getNumericCellValue();
            double m1 = (double) m.getNumericCellValue();
            double mul = n1*m1;
            sheet.getRow(i).getCell(4).setCellValue(mul);
        }
        sheet.getRow(rows).getCell(4).setCellFormula("sum(E2:E7)");

        String outputPath="XBB.xls";//最后输出并保存
        FileOutputStream out = new FileOutputStream(outputPath);
        workbook.write(out);//保存Excel文件


    }

}

