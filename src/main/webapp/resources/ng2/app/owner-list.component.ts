import {Component, OnInit} from "@angular/core";
import OwnerService from "./owner.service";

@Component({
  moduleId: module.id,
  selector: 'ptc-owner-list',
  templateUrl: 'owner-list.component.html',
})
export class OwnerListComponent implements OnInit {
  owners = [];

  constructor(private ownerService: OwnerService) {
  }

  ngOnInit(): void {
    this.ownerService.getOwners("")
      .subscribe(
        (owners: Array<any>) => this.owners = owners
      );
  }

  view(owner): boolean {
    return false;
  }

  edit(owner): boolean {
    return false;
  }

  delete(owner): boolean {
    return false;
  }
}