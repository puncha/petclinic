package tk.puncha.views;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.servlet.view.document.AbstractXlsView;
import tk.puncha.models.Owner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class OwnerExcelView extends AbstractXlsView {

  @Override
  protected void buildExcelDocument(Map<String, Object> model, org.apache.poi.ss.usermodel.Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
    List<Owner> owners = (List<Owner>) model.get("owners");
    Sheet sheet = workbook.createSheet();
    for (int i = 0; i < owners.size(); i++) {
      Owner owner = owners.get(i);
      Row row = sheet.createRow(i);
      fillOwnerInRow(owner, row);
    }
  }

  private void fillOwnerInRow(Owner owner, Row row) {
    int columnIndex = 0;
    row.createCell(columnIndex++).setCellValue(owner.getFirstName());
    row.createCell(columnIndex++).setCellValue(owner.getLastName());
    row.createCell(columnIndex++).setCellValue(owner.getAddress());
    row.createCell(columnIndex++).setCellValue(owner.getCity());
    row.createCell(columnIndex).setCellValue(owner.getTelephone());
  }
}
