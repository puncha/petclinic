import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {OwnersComponent} from "./owners.component";
import {OwnerTableComponent} from "./owner-table/owner-table.component";
import OwnerService from "./owner.service";
import {ownersRouting} from "./owners.routing";
import {SimpleNotificationsModule} from "angular2-notifications";
import {OwnerFormComponent} from "./owner-form/owner-form.component";
import {CreateOwnerButtonComponent} from "./create-owner-button/create-owner-button.component";

@NgModule({
  imports: [CommonModule, FormsModule, HttpModule, NgbModule, SimpleNotificationsModule, ownersRouting],
  providers: [OwnerService],
  declarations: [
    OwnersComponent,
    OwnerTableComponent,
    OwnerFormComponent,
    CreateOwnerButtonComponent,
  ]
})
export class OwnersModule {
}