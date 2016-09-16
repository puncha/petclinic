import _ = require("lodash");
import {Component, OnInit, OnDestroy, ViewChild} from "@angular/core";
import {Owner} from "./Owner";
import OwnerService from "./owner.service";
import {Options, NotificationsService} from "angular2-notifications";
import {Router, NavigationEnd} from "@angular/router";
import {Subscription} from "rxjs";
import "rxjs/add/operator/filter";


@Component({
  moduleId: module.id,
  selector: 'ptc-owners',
  templateUrl: 'owners.component.html'
})
export class OwnersComponent implements OnInit, OnDestroy {
  private notificationsOptions: Options = {};
  private subscription: Subscription;
  private owners = [];
  private lastTimeFirstNameToSearch: string;

  @ViewChild('ownerViewModal') ownerViewModal;
  private owner: Owner;


  constructor(private router: Router,
              private notificationsService: NotificationsService,
              private ownerService: OwnerService) {
    this.notificationsOptions = {
      timeOut: 5000,
      showProgressBar: true,
      position: ["top", "right"]
    };
  }

  //noinspection JSUnusedGlobalSymbols
  ngOnInit(): void {
    this.subscription = this.router.events
      .filter((event)=>event instanceof NavigationEnd)
      .filter((event)=>event.url === '/owners')
      .subscribe((value)=>this.listOwners(this.lastTimeFirstNameToSearch));
  }

  //noinspection JSUnusedGlobalSymbols
  ngOnDestroy(): void {
    if (this.subscription)
      this.subscription.unsubscribe();
  }


  viewOwner(owner: Owner) {
    this.owner = owner;
    this.ownerViewModal.showModal();
  }

  editOwner(owner: Owner) {
    this.toastAlert("TODO: Not implemented");
  }

  deleteOwner(owner: Owner) {
    this.ownerService.deleteOwner(owner.id).subscribe(
      ()=> _.remove(this.owners, owner),
      (error: any)=>this.toastError("Delete owner failed.", error)
    );
  }

  listOwners(firstNameToSearch: string): boolean {
    this.lastTimeFirstNameToSearch = firstNameToSearch;
    this.ownerService.getOwners(firstNameToSearch)
      .subscribe(
        (owners: Array<any>) => this.owners = owners,
        (error: any)=>this.toastError("Delete owner failed.", error)
      );
    return false;
  }

  toastAlert(title: string, error?: any): void {
    this.notificationsService.alert(title, error ? error.toString() : '');
  }

  toastError(title: string, error?: any): void {
    this.notificationsService.error(title, error ? error.toString() : '');
  }
}