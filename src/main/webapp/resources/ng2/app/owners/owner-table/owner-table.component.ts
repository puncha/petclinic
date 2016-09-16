import {Component, Input, Output, EventEmitter} from "@angular/core";
import {Owner} from "../Owner";

@Component({
  moduleId: module.id,
  selector: 'ptc-owner-table',
  templateUrl: 'owner-table.component.html',
})
export class OwnerTableComponent {
  @Input() owners = [];
  @Output() viewOwner = new EventEmitter<any>();
  @Output() editOwner = new EventEmitter<any>();
  @Output() deleteOwner = new EventEmitter<any>();

  onClickView(owner: Owner): boolean {
    this.viewOwner.emit(owner);
    return false;
  }

  onClickEdit(owner: Owner): boolean {
    this.editOwner.emit(owner);
    return false;
  }

  onClickDelete(owner: Owner): boolean {
    this.deleteOwner.emit(owner);
    return false;
  }
}