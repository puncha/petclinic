import {Component, Output, Input} from "@angular/core";

@Component({
  moduleId: module.id,
  selector: 'ptc-owners',
  templateUrl: 'owners.component.html'
})
export class OwnersComponent {
  addNewOwner(): boolean {
    alert("addNewOwner()");
    return false;
  }

  searchOwners(firstNameToSearch): boolean {
    alert(`searchOwners(${firstNameToSearch})`);
    return false;
  }
}