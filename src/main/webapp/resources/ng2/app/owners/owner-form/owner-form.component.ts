import {Component, Input} from "@angular/core";
import {Owner} from "../Owner";
import {Router} from "@angular/router";
import {NgModel} from "@angular/forms";
import OwnerService from "../owner.service";
import {NotificationsService} from "angular2-notifications";

@Component({
  moduleId: module.id,
  selector: 'ptc-owner-form',
  templateUrl: 'owner-form.component.html'
})
export class OwnerFormComponent {
  @Input() private owner: Owner;

  constructor(private ownerService: OwnerService,
              private notifications: NotificationsService,
              private router: Router) {
    this.owner = {
      firstName: '',
      lastName: '',
      address: ''
    };
  }

  onSubmit(): boolean {
    this.ownerService.insertOwner(this.owner).subscribe(
      ()=>this.router.navigate(["owners"]),
      (error: any)=>this.notifications.error("Insert owner failed.", error)
    );
    return false;
  }

  //noinspection JSMethodCanBeStatic
  shouldShowErrorAlert(formControlModel: NgModel): boolean {
    // We don't use !pristine here because we want to show validation marks
    // if user touches or changes the input controls.
    return formControlModel.invalid && (formControlModel.dirty || formControlModel.touched);
  }
}