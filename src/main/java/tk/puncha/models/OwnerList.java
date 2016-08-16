package tk.puncha.models;

import com.fasterxml.jackson.annotation.JsonView;
import tk.puncha.views.json.view.OwnerJsonView;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "owners")
public class OwnerList{
  private List<Owner> owners;

  public OwnerList() {
  }

  public OwnerList(List<Owner> owners) {
    this.owners = owners;
  }

  @JsonView(OwnerJsonView.Default.class)
  @XmlElement(name = "owner")
  public List<Owner> getOwners() {
    return owners;
  }

  public void setOwners(List<Owner> owners) {
    this.owners = owners;
  }
}
