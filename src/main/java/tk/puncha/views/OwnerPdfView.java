package tk.puncha.views;

import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfPTable;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import tk.puncha.models.Owner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class OwnerPdfView extends AbstractPdfView {
  @Override
  protected void buildPdfDocument(Map<String, Object> model, com.lowagie.text.Document document, com.lowagie.text.pdf.PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
    List<Owner> owners = (List<Owner>) model.get("owners");

    PdfPTable table = new PdfPTable(5);
    table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
    table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
    table.getDefaultCell().setBackgroundColor(Color.CYAN);
    for (Owner owner : owners) {
      fillOwnerInRow(owner, table);
      table.completeRow();
    }
    document.add(table);
  }

  private void fillOwnerInRow(Owner owner, PdfPTable table) {
    table.addCell(owner.getFirstName());
    table.addCell(owner.getLastName());
    table.addCell(owner.getAddress());
    table.addCell(owner.getCity());
    table.addCell(owner.getTelephone());
  }
}
