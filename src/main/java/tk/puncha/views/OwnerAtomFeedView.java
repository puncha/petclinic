package tk.puncha.views;

import com.rometools.rome.feed.atom.Content;
import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Person;
import org.springframework.web.servlet.view.feed.AbstractAtomFeedView;
import tk.puncha.models.Owner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OwnerAtomFeedView extends AbstractAtomFeedView {
  @Override
  protected List<Entry> buildFeedEntries(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
    List<Owner> owners = (List<Owner>) model.get("owners");
    List<Entry> entries = new ArrayList<>();
    for (Owner owner : owners) {
      Entry entry = new Entry();
      entry.setId(String.format("%d", owner.getId()));
      Person person = new Person();
      person.setName(owner.getFirstName());
      entry.setAuthors(Collections.singletonList(person));
      entry.setTitle(owner.getAddress());
      Content summary = new Content();
      summary.setValue(owner.getAddress());
      entry.setSummary(summary);
      entries.add(entry);
    }
    return entries;
  }
}
