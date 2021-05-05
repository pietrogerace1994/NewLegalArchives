package eng.la.util; 

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;


/*
 * 11/04/2016
 * MS
 * WriteExcell
 * Utility per la generazione di file excell
 * 
 * */

/*
 * Esempio:
    WriteExcell excell= new WriteExcell();
    // Addiziono 5 colonne dichiarando il nome_Colonna , colonna_Tipo , e style_Colonna 
    excell.addHeader("Giorno", WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
    excell.addHeader("Vc(sm)", WriteExcell.TYPECELL_STRING,excell.CSTYLE_DEFAULT);
    excell.addHeader("i", WriteExcell.TYPECELL_NUMBER);
    excell.addHeader("Ec(kwh)", WriteExcell.TYPECELL_NUMBER);
    excell.addHeader("PCS(E/V)", WriteExcell.TYPECELL_DATE,excell.CSTYLE_DATE);

    // Creo una riga con 5 colonne
    Vector row= new Vector();	   
       row.add(new Character('R'));	   
       row.add("Ciaoo");	   
       row.add(new Long("90"));	
       row.add(new Short("20"));	
       row.add(new Date());
    // Addiziona la riga al body
    excell.addRowBody(row);	
    // Addiziono un nuovo Sheet(Foglio) con nome
    excell.addSheet("GiornalieriDiRemi");
    // Creo lo Sheet 
    excell.createSheet();
    // Setto il nome per il download 
    excell.setDownloadNomeFile("DatiGiornalieriDiRemi.xls"); 
    //  Eseguo il download del file.xls
    excell.writeDownloadWorkbook(response);

 */

public class WriteExcell {

	private boolean isSTATOSHEET=false;
	private Vector<String> header=null;
	private Vector<String> headerFooter=null;
	private List<Vector> body=null;
	private List<Vector> bodyFooter=null;
	private Vector<Integer> typeCell=null;
	private Vector<CellStyle> cellSTYLE=null;
	private Vector<Integer> typeCellFooter=null;
	private Vector<CellStyle> cellSTYLEFOOTER=null;
	private Vector<String> formula=null;
	private String nomeFile="export-excell.xls";
	public static Integer TYPECELL_DATE=6;
	public static Integer TYPECELL_STRING=1;
	public static Integer TYPECELL_FORMULA=2;
	public static Integer TYPECELL_NUMBER=0;
	public static Integer TYPECELL_BLANK=3;
	public static Integer TYPECELL_BOOLEAN=4; 
	public static Integer TYPECELL_DEFAULT=24; 
	private static final String STR_DEF = "";  
	private static final int CELL_TYPE_NUMERIC = 0;
	private static final int CELL_TYPE_STRING = 1;
	private static final int CELL_TYPE_FORMULA = 2;
	private static final int CELL_TYPE_BLANK = 3;
	private static final int CELL_TYPE_BOOLEAN = 4;
	private static final int CELL_TYPE_DATE = 6;
	private static final int CELL_TYPE_DEFAULT = 24;
	private HSSFWorkbook templateWork =null;
	private HSSFSheet infoSheet =null;

	//Styli
	public CellStyle CSTYLE_STRING=null;
	public CellStyle CSTYLE_DATE=null;
	public CellStyle CSTYLE_NUMBER=null;
	public CellStyle CSTYLE_HEADER=null;
	public CellStyle CSTYLE_FOOTER=null;
	public CellStyle CSTYLE_DEFAULT=null;


	public WriteExcell() {
		this.header= new Vector<String>();
		this.headerFooter= new Vector<String>();
		this.typeCell= new Vector<Integer>();
		this.cellSTYLE= new Vector<CellStyle>();
		this.typeCellFooter= new Vector<Integer>();
		this.cellSTYLEFOOTER= new Vector<CellStyle>();
		this.body=new ArrayList<Vector>();
		this.bodyFooter=new ArrayList<Vector>();
		this.formula=new Vector<String>();
		this.templateWork= new HSSFWorkbook();

		CSTYLE_DEFAULT=templateWork.createCellStyle();
		CSTYLE_DEFAULT.setAlignment(CellStyle.ALIGN_CENTER);
		CSTYLE_DEFAULT.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		CSTYLE_STRING=templateWork.createCellStyle();
		CSTYLE_STRING.setAlignment(CellStyle.ALIGN_LEFT);
		CSTYLE_STRING.setVerticalAlignment(CellStyle.VERTICAL_TOP);

		CSTYLE_DATE=templateWork.createCellStyle();
		CSTYLE_DATE.setDataFormat(templateWork.createDataFormat().getFormat("dd/mm/yyyy"));
		CSTYLE_DATE.setAlignment(CellStyle.ALIGN_CENTER);		

		CSTYLE_NUMBER=templateWork.createCellStyle();
		CSTYLE_NUMBER.setAlignment(CellStyle.ALIGN_LEFT);
		CSTYLE_NUMBER.setVerticalAlignment(CellStyle.VERTICAL_TOP);

		CSTYLE_HEADER=templateWork.createCellStyle();
		CSTYLE_HEADER.setAlignment(CellStyle.ALIGN_CENTER);
		CSTYLE_HEADER.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		CSTYLE_HEADER.setFillForegroundColor(HSSFColor.CORNFLOWER_BLUE.index);

		HSSFFont font = templateWork.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.WHITE.index);
		font.setFontHeight((short)165);
		CSTYLE_HEADER.setFont(font);
		CSTYLE_HEADER.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		CSTYLE_FOOTER=templateWork.createCellStyle();
		CSTYLE_FOOTER.setAlignment(CellStyle.ALIGN_CENTER);
		CSTYLE_FOOTER.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		CSTYLE_FOOTER.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		CSTYLE_FOOTER.setFont(font);
		CSTYLE_FOOTER.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

	}


	public void addHeader(String colName,Integer typeCel,CellStyle celStyle){
		this.header.add(colName);
		this.typeCell.add(typeCel);
		this.cellSTYLE.add(celStyle);
	}
	public void addHeader(String colName,Integer typeCel){
		this.header.add(colName);
		this.typeCell.add(typeCel);
		this.cellSTYLE.add(CSTYLE_DEFAULT);
	}


	public void addHeader(String colName){
		this.header.add(colName);
		this.typeCell.add(TYPECELL_DEFAULT);
		this.cellSTYLE.add(CSTYLE_DEFAULT);
	}

	//
	public void addFooter(String colName,Integer typeCel,CellStyle celStyle){
		this.headerFooter.add(colName);
		this.typeCellFooter.add(typeCel);
		this.cellSTYLEFOOTER.add(celStyle);
		this.formula.add(STR_DEF);
	}
	public void addFooter(String colName,Integer typeCel){
		this.headerFooter.add(colName);
		this.typeCellFooter.add(typeCel);
		this.cellSTYLEFOOTER.add(CSTYLE_DEFAULT);
		this.formula.add(STR_DEF);
	}
	public void addFooter(String colName,Integer typeCel,int columnTotale){
		this.headerFooter.add(colName);
		this.typeCellFooter.add(typeCel);
		this.cellSTYLEFOOTER.add(CSTYLE_DEFAULT);
		String colN=CellReference.convertNumToColString(columnTotale);
		String sformula = String.format("SUM(%s:%s)", colN+2, colN+(this.body.size()+1));
		this.formula.add(sformula);
	}

	public void addFooter(String colName,Integer typeCel,CellStyle celStyle,int columnTotale){
		this.headerFooter.add(colName);
		this.typeCellFooter.add(typeCel);
		this.cellSTYLEFOOTER.add(celStyle);
		String colN=CellReference.convertNumToColString(columnTotale);
		String sformula = String.format("SUM(%s:%s)", colN+2, colN+(this.body.size()+1));
		this.formula.add(sformula);
	}


	public void addFooter(String colName){
		this.headerFooter.add(colName);
		this.typeCellFooter.add(TYPECELL_DEFAULT);
		this.cellSTYLEFOOTER.add(CSTYLE_DEFAULT);
		this.formula.add(STR_DEF);
	}	


	public void addRowBody(Vector row){
		this.body.add(row); 
	}
	public void addRowFooter(Vector row){
		this.bodyFooter.add(row); 
	}
	public void setHeader(Vector<String> header) {
		this.header = header;
	}

	public void setBody(List<Vector> body,Vector<Integer> typeCell) {
		this.body = body;
		this.typeCell=typeCell;
	}

	public void setBody(List<Vector> body) {
		this.body = body;

	}

	public void addSheet(String sheetName) {
		this.isSTATOSHEET=false;
		this.infoSheet = templateWork.createSheet(sheetName);
	}
	
	public void addSheetDefaultColumnWidth(String sheetName) {
		this.isSTATOSHEET=false;
		this.infoSheet = templateWork.createSheet(sheetName);
	}

	public void addSheet() {
		this.isSTATOSHEET=false;
		this.infoSheet = templateWork.createSheet();
	}

	public HSSFSheet getCurrentSheet() {
		return this.infoSheet;
	}

	public HSSFSheet getSheet(String sheetName) {
		return this.templateWork.getSheet(sheetName);
	}

	public HSSFSheet getSheetAt(int index) {
		return this.templateWork.getSheetAt(index);
	}

	public void setDownloadNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	} 

	public byte[] getByte(){
		this.execute();
		return this.templateWork.getBytes();
	}

	public void setCSTYLE_STRING(CellStyle cSTYLE_STRING) {
		CSTYLE_STRING = cSTYLE_STRING;
	}

	public void setCSTYLE_DATE(CellStyle cSTYLE_DATE) {
		CSTYLE_DATE = cSTYLE_DATE;
	}

	public void setCSTYLE_DATE_FORMAT(String pattern) { 
		CSTYLE_DATE.setDataFormat(templateWork.createDataFormat().getFormat(pattern));
	}

	public void setCSTYLE_NUMBER(CellStyle cSTYLE_NUMBER) {
		CSTYLE_NUMBER = cSTYLE_NUMBER;
	}

	public void setCSTYLE_HEADER(CellStyle cSTYLE_HEADER) {
		CSTYLE_HEADER = cSTYLE_HEADER;
	}

	public void setCSTYLE_DEFAULT(CellStyle cSTYLE_DEFAULT) {
		CSTYLE_DEFAULT = cSTYLE_DEFAULT;
	}

	public HSSFWorkbook getWorkbook() {
		return this.templateWork;
	}

	public void setWorkbook(HSSFWorkbook workbook) {
		this.templateWork = workbook;
	}

	public short createDataFormat(String formatt) {
		return this.templateWork.createDataFormat().getFormat(formatt);
	}

	public String createDataFormat(short index) {
		return this.templateWork.createDataFormat().getFormat(index);
	}

	public CellStyle createCellStyle() {
		return this.templateWork.createCellStyle();
	}

	public HSSFFont createFont() {
		return this.templateWork.createFont();
	}

	public CellStyle cellStyleFormatt_Aleft(String formatt) {
		CellStyle cellStyle = this.templateWork.createCellStyle();
		cellStyle.setDataFormat(this.createDataFormat(formatt));
		cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		return cellStyle;
	}

	public CellStyle cellStyleFormatt_Arigth(String formatt) {
		CellStyle cellStyle = this.templateWork.createCellStyle();
		cellStyle.setDataFormat(this.createDataFormat(formatt));
		cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		return cellStyle;
	}

	public CellStyle cellStyleFormatt_Acenter(String formatt) {
		CellStyle cellStyle = this.templateWork.createCellStyle();
		cellStyle.setDataFormat(this.createDataFormat(formatt));
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		return cellStyle;
	}


	private HSSFSheet executeFooter(){

		try {
			int rowBoy=this.body.size();
			if (this.headerFooter!=null && this.headerFooter.size()>0){
				if (this.bodyFooter == null){ this.bodyFooter= new ArrayList<Vector>(); }
				if (this.bodyFooter != null && bodyFooter.size()<=0) {

					Vector bodF = new Vector();
					for (int x = 0; x < this.headerFooter.size(); x++) {
						bodF.add(STR_DEF);

					}
					this.bodyFooter.add(bodF);

				}
				if (this.bodyFooter.size() > 0) {
					for (int k = 0; k < this.bodyFooter.size(); k++) {
						if (this.bodyFooter.get(k).size() < this.headerFooter.size()) {
							for (int h = this.bodyFooter.get(k).size(); h < this.headerFooter.size(); h++) {
								this.bodyFooter.get(k).add(STR_DEF);
							}
						}

					}

				}

			}



			if (this.headerFooter != null && this.headerFooter.size()>0) {



				HSSFRow rowT = this.infoSheet.createRow(rowBoy+3);
				for (int i = 0; i < this.headerFooter.size(); i++) {
					HSSFCell cellaT = null;
					cellaT = rowT.createCell(i);
					cellaT.setCellStyle(CSTYLE_FOOTER);
					cellaT.setCellType(HSSFCell.CELL_TYPE_STRING);
					cellaT.setCellValue(this.headerFooter.get(i));
				}





				if (this.cellSTYLEFOOTER.isEmpty()
						|| this.cellSTYLEFOOTER.size() < this.headerFooter
						.size()) {
					for (int j = 0; j < this.headerFooter.size(); j++) {
						this.cellSTYLEFOOTER.add(CSTYLE_DEFAULT);
					}
				}


				for (int r = 0; r < this.bodyFooter.size(); r++) {

					HSSFCell cella = null;

					HSSFRow row = this.infoSheet.createRow((rowBoy+4)+ r);
					Vector colV = this.bodyFooter.get(r);
					for (int y = 0; y < colV.size(); y++) {

						cella = row.createCell(y);
						cella.setCellStyle(cellSTYLEFOOTER.get(y));
						cella.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						if (colV.get(y) != null) {
							if (Double.class.getName().equalsIgnoreCase(
									colV.get(y).getClass().getName()))
								cella.setCellValue((Double) colV.get(y));
							else if (double.class.getName().equalsIgnoreCase(
									colV.get(y).getClass().getName()))
								cella.setCellValue((double) colV.get(y));
							else if (Integer.class.getName().equalsIgnoreCase(
									colV.get(y).getClass().getName()))
								cella.setCellValue((Integer) colV.get(y));
							else if (Short.class.getName().equalsIgnoreCase(
									colV.get(y).getClass().getName()))
								cella.setCellValue((Short) colV.get(y));
							else if (short.class.getName().equalsIgnoreCase(
									colV.get(y).getClass().getName()))
								cella.setCellValue((short) colV.get(y));	
							else if (Long.class.getName().equalsIgnoreCase(
									colV.get(y).getClass().getName()))
								cella.setCellValue((Long) colV.get(y));
							else if (long.class.getName().equalsIgnoreCase(
									colV.get(y).getClass().getName()))
								cella.setCellValue((long) colV.get(y));
							else if (Float.class.getName().equalsIgnoreCase(
									colV.get(y).getClass().getName()))
								cella.setCellValue((Float) colV.get(y));
							else if (float.class.getName().equalsIgnoreCase(
									colV.get(y).getClass().getName()))
								cella.setCellValue((float) colV.get(y));
							else if (BigDecimal.class.getName()
									.equalsIgnoreCase(
											colV.get(y).getClass().getName()))
								cella.setCellValue(((BigDecimal) colV.get(y))
										.doubleValue());
							else if (BigInteger.class.getName()
									.equalsIgnoreCase(
											colV.get(y).getClass().getName()))
								cella.setCellValue(((BigInteger) colV.get(y))
										.intValue());
							else if (Date.class.getName().equalsIgnoreCase(
									colV.get(y).getClass().getName())) {
								cella.setCellStyle(CSTYLE_DATE);
								cella.setCellValue((Date) colV.get(y));
							} else if (int.class.getName().equalsIgnoreCase(
									colV.get(y).getClass().getName()))
								cella.setCellValue((int) colV.get(y));
							else if (String.class.getName().equalsIgnoreCase(
									colV.get(y).getClass().getName())) {

								if(typeCellFooter.get(y).intValue()==HSSFCell.CELL_TYPE_FORMULA){
									cella.setCellType(HSSFCell.CELL_TYPE_FORMULA);
									cella.setCellFormula(formula.get(y));
								}else{
									cella.setCellType(HSSFCell.CELL_TYPE_STRING);
									cella.setCellValue(new HSSFRichTextString(colV
											.get(y).toString()));
								}
							} else if (Character.class.getName()
									.equalsIgnoreCase(
											colV.get(y).getClass().getName())) {
								cella.setCellType(HSSFCell.CELL_TYPE_STRING);
								cella.setCellValue(new HSSFRichTextString(
										((Character) colV.get(y)).toString()));
							} else if (char.class.getName().equalsIgnoreCase(
									colV.get(y).getClass().getName())) {
								cella.setCellType(HSSFCell.CELL_TYPE_STRING);
								cella.setCellValue(new HSSFRichTextString(colV
										.get(y).toString()));
							} else if (Boolean.class.getName()
									.equalsIgnoreCase(
											colV.get(y).getClass().getName())) {
								cella.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
								cella.setCellValue((Boolean) colV.get(y));
							} else if (boolean.class.getName()
									.equalsIgnoreCase(
											colV.get(y).getClass().getName())) {
								cella.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
								cella.setCellValue(((Boolean) colV.get(y)));
							} else {
								cella.setCellType(HSSFCell.CELL_TYPE_STRING);
								cella.setCellValue(new HSSFRichTextString((colV
										.get(y)).toString()));
							}
						} else {
							cella.setCellType(HSSFCell.CELL_TYPE_BLANK);
							cella.setCellValue(STR_DEF);
						}



					}

				}

			}
		} catch (Exception e) {
		}

		return this.infoSheet;
	}     

	private HSSFSheet execute(){


		try{

			this.isSTATOSHEET=true;

			if(this.body!=null){

				//cStyle.setWrapText(true);

				if(this.infoSheet==null){
					this.infoSheet = this.templateWork.createSheet();
				}

				if(this.header!=null){

					HSSFRow rowT = this.infoSheet.createRow(0);
					for(int i=0;i<this.header.size();i++){	
						HSSFCell cellaT=null; 
						cellaT = rowT.createCell(i);
						cellaT.setCellStyle(CSTYLE_HEADER);
						cellaT.setCellType(HSSFCell.CELL_TYPE_STRING);
						cellaT.setCellValue(this.header.get(i));
					}

				}

				if(this.cellSTYLE.isEmpty()|| this.cellSTYLE.size()<this.header.size()){
					for(int j=0;j<this.header.size();j++){
						this.cellSTYLE.add(CSTYLE_DEFAULT);
					}   	
				}



				for(int r=0;r<this.body.size();r++){

					HSSFCell cella = null;

					HSSFRow	row = this.infoSheet.createRow((r+1));
					Vector colV=this.body.get(r);
					for(int y=0;y<colV.size();y++){
						int type;
						if(typeCell!=null && typeCell.get(y)!=null)
							type=typeCell.get(y).intValue();
						else
							type=CELL_TYPE_DEFAULT;	

						switch (type) {
						case CELL_TYPE_DATE :

							cella = row.createCell(y);
							cella.setCellStyle(cellSTYLE.get(y));
							cella.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							if(colV.get(y)!=null){
								Date value=(Date)colV.get(y);	
								cella.setCellValue(value);
							} else{ cella.setCellType(HSSFCell.CELL_TYPE_BLANK); cella.setCellValue(STR_DEF);}
							break;

						case CELL_TYPE_STRING :

							cella = row.createCell(y);
							cella.setCellStyle(cellSTYLE.get(y));
							cella.setCellType(HSSFCell.CELL_TYPE_STRING);
							if(colV.get(y)!=null){
								if(!Character.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())||
										!char.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))  
									cella.setCellValue(new HSSFRichTextString(String.valueOf(colV.get(y)))); 
								else if(Character.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))  
									cella.setCellValue(new HSSFRichTextString(((Character)colV.get(y)).toString()));
								else if(char.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())) 	 
									cella.setCellValue(new HSSFRichTextString(String.valueOf(colV.get(y))));
							} else{ cella.setCellType(HSSFCell.CELL_TYPE_BLANK); cella.setCellValue(STR_DEF);}

							break;
						case CELL_TYPE_NUMERIC :

							cella = row.createCell(y);
							cella.setCellStyle(cellSTYLE.get(y));
							cella.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							if(colV.get(y)!=null){
								if(Double.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))		
									cella.setCellValue((Double)colV.get(y));
								else if(double.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))		
									cella.setCellValue((double)colV.get(y));
								else if(Integer.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((Integer)colV.get(y));
								else if(Short.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((Short)colV.get(y));
								else if(short.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((short)colV.get(y));
								else if(Long.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((Long)colV.get(y));
								else if(long.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((long)colV.get(y));	
								else if(Float.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((Float)colV.get(y));
								else if(float.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((float)colV.get(y));	
								else if(BigDecimal.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue(((BigDecimal)colV.get(y)).doubleValue());
								else if(BigInteger.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue(((BigInteger)colV.get(y)).intValue());
								else if(Date.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())){
									cella.setCellStyle(CSTYLE_DATE);
									cella.setCellValue((Date)colV.get(y));}
								else if(int.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((int) colV.get(y));
								else if(String.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())){
									cella.setCellType(HSSFCell.CELL_TYPE_STRING);
									cella.setCellValue(new HSSFRichTextString(colV.get(y).toString()));
								}
								else if(Character.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())){
									cella.setCellType(HSSFCell.CELL_TYPE_STRING);
									cella.setCellValue(new HSSFRichTextString(((Character)colV.get(y)).toString()));
								}
								else if(char.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())){
									cella.setCellType(HSSFCell.CELL_TYPE_STRING);
									cella.setCellValue(new HSSFRichTextString(colV.get(y).toString()));
								}
								else if(Boolean.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())){
									cella.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
									cella.setCellValue((Boolean)colV.get(y));
								}
								else if(boolean.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())){
									cella.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
									cella.setCellValue(new Boolean(colV.get(y).toString()));
								}
								else{
									cella.setCellType(HSSFCell.CELL_TYPE_STRING);
									cella.setCellValue(new HSSFRichTextString((colV.get(y)).toString()));
								}
							}else{
								cella.setCellType(HSSFCell.CELL_TYPE_BLANK);
								cella.setCellValue(STR_DEF);
							}

							break;	
						case CELL_TYPE_FORMULA :
							cella = row.createCell(y);
							cella.setCellStyle(cellSTYLE.get(y));
							cella.setCellType(HSSFCell.CELL_TYPE_FORMULA);
							if(colV.get(y)!=null){
								cella.setCellValue((colV.get(y)).toString());
							} else{ cella.setCellType(HSSFCell.CELL_TYPE_BLANK); cella.setCellValue(STR_DEF);}
							break;
						case CELL_TYPE_BOOLEAN :
							cella = row.createCell(y);
							cella.setCellStyle(cellSTYLE.get(y));
							cella.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
							if(colV.get(y)!=null){
								cella.setCellValue((Boolean)colV.get(y));
							}else{ cella.setCellType(HSSFCell.CELL_TYPE_BLANK); cella.setCellValue(STR_DEF);}
							break;		
						case CELL_TYPE_BLANK :
							cella = row.createCell(y);
							cella.setCellStyle(cellSTYLE.get(y));
							cella.setCellType(HSSFCell.CELL_TYPE_BLANK);
							if(colV.get(y)!=null){
								String s=String.valueOf(colV.get(y));
								cella.setCellValue(s);}
							else {cella.setCellValue(STR_DEF);}	
							break;		

						default:

							cella = row.createCell(y);
							cella.setCellStyle(cellSTYLE.get(y));
							cella.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							if(colV.get(y)!=null){
								if(Double.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))		
									cella.setCellValue((Double)colV.get(y));
								else if(double.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))		
									cella.setCellValue((double)colV.get(y));
								else if(Integer.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((Integer)colV.get(y));
								else if(Short.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((Short)colV.get(y));
								else if(short.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((short)colV.get(y));
								else if(Long.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((Long)colV.get(y));
								else if(long.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((long)colV.get(y));
								else if(Float.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((Float)colV.get(y));
								else if(float.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((float)colV.get(y));
								else if(BigDecimal.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue(((BigDecimal)colV.get(y)).doubleValue());
								else if(BigInteger.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue(((BigInteger)colV.get(y)).intValue());
								else if(Date.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())){
									cella.setCellStyle(CSTYLE_DATE);	
									cella.setCellValue((Date)colV.get(y));}
								else if(int.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((int) colV.get(y));	
								else if(String.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())){
									cella.setCellType(HSSFCell.CELL_TYPE_STRING);
									cella.setCellValue(new HSSFRichTextString(colV.get(y).toString()));
								}
								else if(Character.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())){
									cella.setCellType(HSSFCell.CELL_TYPE_STRING);
									cella.setCellValue(new HSSFRichTextString(((Character)colV.get(y)).toString()));
								}
								else if(char.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())){
									cella.setCellType(HSSFCell.CELL_TYPE_STRING);
									cella.setCellValue(new HSSFRichTextString(colV.get(y).toString()));
								}
								else if(Boolean.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())){
									cella.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
									cella.setCellValue((Boolean)colV.get(y));
								}
								else if(boolean.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())){
									cella.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
									cella.setCellValue(((Boolean)colV.get(y)));
								}
								else{
									cella.setCellType(HSSFCell.CELL_TYPE_STRING);
									cella.setCellValue(new HSSFRichTextString((colV.get(y)).toString()));
								}
							}else{
								cella.setCellType(HSSFCell.CELL_TYPE_BLANK);
								cella.setCellValue(STR_DEF);
							}

							break;
						}	


					}


				}


				this.executeFooter();

			}

		}catch( Exception e){	
		}
		return this.infoSheet;
	}


	private HSSFSheet execute(String sheetName){

		try{

			this.isSTATOSHEET=true;

			if(this.body!=null){

				if(this.infoSheet==null){
					this.infoSheet = this.templateWork.createSheet(sheetName);
				}

				if(this.header!=null){

					HSSFRow rowT = this.infoSheet.createRow(0);
					for(int i=0;i<this.header.size();i++){	
						HSSFCell cellaT=null; 
						cellaT = rowT.createCell(i);
						cellaT.setCellStyle(CSTYLE_HEADER);
						cellaT.setCellType(HSSFCell.CELL_TYPE_STRING);
						cellaT.setCellValue(this.header.get(i));
					}

				}

				if(this.cellSTYLE.isEmpty()|| this.cellSTYLE.size()<this.header.size()){
					for(int j=0;j<this.header.size();j++){
						this.cellSTYLE.add(CSTYLE_DEFAULT);
					}   	
				}



				for(int r=0;r<this.body.size();r++){

					HSSFCell cella = null;

					HSSFRow	row = this.infoSheet.createRow((r+1));
					Vector colV=this.body.get(r);
					for(int y=0;y<colV.size();y++){
						int type;
						if(typeCell!=null && typeCell.get(y)!=null)
							type=typeCell.get(y).intValue();
						else
							type=CELL_TYPE_DEFAULT;	

						switch (type) {
						case CELL_TYPE_DATE :

							cella = row.createCell(y);
							cella.setCellStyle(cellSTYLE.get(y));
							cella.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							if(colV.get(y)!=null){
								Date value=(Date)colV.get(y);	
								cella.setCellValue(value);
							} else{ cella.setCellType(HSSFCell.CELL_TYPE_BLANK); cella.setCellValue(STR_DEF);}
							break;

						case CELL_TYPE_STRING :

							cella = row.createCell(y);
							cella.setCellStyle(cellSTYLE.get(y));
							cella.setCellType(HSSFCell.CELL_TYPE_STRING);
							if(colV.get(y)!=null){
								if(!Character.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())||
										!char.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))  
									cella.setCellValue(new HSSFRichTextString(String.valueOf(colV.get(y)))); 
								else if(Character.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))  
									cella.setCellValue(new HSSFRichTextString(((Character)colV.get(y)).toString()));
								else if(char.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())) 	 
									cella.setCellValue(new HSSFRichTextString(String.valueOf(colV.get(y))));
							} else{ cella.setCellType(HSSFCell.CELL_TYPE_BLANK); cella.setCellValue(STR_DEF);}

							break;
						case CELL_TYPE_NUMERIC :

							cella = row.createCell(y);
							cella.setCellStyle(cellSTYLE.get(y));
							cella.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							if(colV.get(y)!=null){
								if(Double.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))		
									cella.setCellValue((Double)colV.get(y));
								else if(double.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))		
									cella.setCellValue((double)colV.get(y));
								else if(Integer.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((Integer)colV.get(y));
								else if(Short.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((Short)colV.get(y));
								else if(short.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((short)colV.get(y));
								else if(Long.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((Long)colV.get(y));
								else if(long.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((long)colV.get(y));	
								else if(Float.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((Float)colV.get(y));
								else if(float.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((float)colV.get(y));	
								else if(BigDecimal.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue(((BigDecimal)colV.get(y)).doubleValue());
								else if(BigInteger.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue(((BigInteger)colV.get(y)).intValue());
								else if(Date.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())){
									cella.setCellStyle(CSTYLE_DATE);
									cella.setCellValue((Date)colV.get(y));}
								else if(int.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((int) colV.get(y));
								else if(String.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())){
									cella.setCellType(HSSFCell.CELL_TYPE_STRING);
									cella.setCellValue(new HSSFRichTextString(colV.get(y).toString()));
								}
								else if(Character.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())){
									cella.setCellType(HSSFCell.CELL_TYPE_STRING);
									cella.setCellValue(new HSSFRichTextString(((Character)colV.get(y)).toString()));
								}
								else if(char.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())){
									cella.setCellType(HSSFCell.CELL_TYPE_STRING);
									cella.setCellValue(new HSSFRichTextString(colV.get(y).toString()));
								}
								else if(Boolean.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())){
									cella.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
									cella.setCellValue((Boolean)colV.get(y));
								}
								else if(boolean.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())){
									cella.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
									cella.setCellValue(new Boolean(colV.get(y).toString()));
								}
								else{
									cella.setCellType(HSSFCell.CELL_TYPE_STRING);
									cella.setCellValue(new HSSFRichTextString((colV.get(y)).toString()));
								}
							}else{
								cella.setCellType(HSSFCell.CELL_TYPE_BLANK);
								cella.setCellValue(STR_DEF);
							}

							break;	
						case CELL_TYPE_FORMULA :
							cella = row.createCell(y);
							cella.setCellStyle(cellSTYLE.get(y));
							cella.setCellType(HSSFCell.CELL_TYPE_FORMULA);
							if(colV.get(y)!=null){
								cella.setCellValue((colV.get(y)).toString());
							} else{ cella.setCellType(HSSFCell.CELL_TYPE_BLANK); cella.setCellValue(STR_DEF);}
							break;
						case CELL_TYPE_BOOLEAN :
							cella = row.createCell(y);
							cella.setCellStyle(cellSTYLE.get(y));
							cella.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
							if(colV.get(y)!=null){
								cella.setCellValue((Boolean)colV.get(y));
							}else{ cella.setCellType(HSSFCell.CELL_TYPE_BLANK); cella.setCellValue(STR_DEF);}
							break;		
						case CELL_TYPE_BLANK :
							cella = row.createCell(y);
							cella.setCellStyle(cellSTYLE.get(y));
							cella.setCellType(HSSFCell.CELL_TYPE_BLANK);
							if(colV.get(y)!=null){
								String s=String.valueOf(colV.get(y));
								cella.setCellValue(s);}
							else {cella.setCellValue(STR_DEF);}	
							break;		

						default:

							cella = row.createCell(y);
							cella.setCellStyle(cellSTYLE.get(y));
							cella.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							if(colV.get(y)!=null){
								if(Double.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))		
									cella.setCellValue((Double)colV.get(y));
								else if(double.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))		
									cella.setCellValue((double)colV.get(y));
								else if(Integer.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((Integer)colV.get(y));
								else if(Short.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((Short)colV.get(y));
								else if(short.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((short)colV.get(y));
								else if(Long.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((Long)colV.get(y));
								else if(long.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((long)colV.get(y));
								else if(Float.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((Float)colV.get(y));
								else if(float.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((float)colV.get(y));
								else if(BigDecimal.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue(((BigDecimal)colV.get(y)).doubleValue());
								else if(BigInteger.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue(((BigInteger)colV.get(y)).intValue());
								else if(Date.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())){
									cella.setCellStyle(CSTYLE_DATE);	
									cella.setCellValue((Date)colV.get(y));}
								else if(int.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName()))
									cella.setCellValue((int) colV.get(y));	
								else if(String.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())){
									cella.setCellType(HSSFCell.CELL_TYPE_STRING);
									cella.setCellValue(new HSSFRichTextString(colV.get(y).toString()));
								}
								else if(Character.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())){
									cella.setCellType(HSSFCell.CELL_TYPE_STRING);
									cella.setCellValue(new HSSFRichTextString(((Character)colV.get(y)).toString()));
								}
								else if(char.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())){
									cella.setCellType(HSSFCell.CELL_TYPE_STRING);
									cella.setCellValue(new HSSFRichTextString(colV.get(y).toString()));
								}
								else if(Boolean.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())){
									cella.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
									cella.setCellValue((Boolean)colV.get(y));
								}
								else if(boolean.class.getName().equalsIgnoreCase(colV.get(y).getClass().getName())){
									cella.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
									cella.setCellValue(((Boolean)colV.get(y)));
								}
								else{
									cella.setCellType(HSSFCell.CELL_TYPE_STRING);
									cella.setCellValue(new HSSFRichTextString((colV.get(y)).toString()));
								}
							}else{
								cella.setCellType(HSSFCell.CELL_TYPE_BLANK);
								cella.setCellValue(STR_DEF);
							}

							break;
						}	


					}


				}


				this.executeFooter();

			}

		}catch( Exception e){	
		}
		return this.infoSheet;
	}

	public WriteExcell createSheet(){
		this.execute();
		this.body=new Vector();
		this.bodyFooter=new Vector();
		return this;
	}

	public WriteExcell createSheet(String sheetName){
		this.execute(sheetName);
		this.body=new Vector();
		this.bodyFooter=new Vector();
		this.header = new Vector();
		this.headerFooter= new Vector();
		return this;
	}

	public void writeDownloadWorkbook(HttpServletResponse response) throws IOException{
		write(response);
	}
	public void writeWorkbook(OutputStream outputStream) throws IOException{
		if(!this.isSTATOSHEET){this.execute();}   
		this.templateWork.write(outputStream);
	}

	public void write(HttpServletResponse response) throws IOException {
		String nomeFiles = this.nomeFile;
		if(!this.isSTATOSHEET){this.execute();}    
		if(!nomeFiles.endsWith(".xls")){
			nomeFiles=nomeFiles+".xls";
		}

		response.setHeader("Content-Disposition", "attachment; filename=" + nomeFiles);
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("application/excel");

		this.templateWork.write(response.getOutputStream());

		response.getOutputStream().flush();
		response.getOutputStream().close();
		response.flushBuffer();

	}


}
