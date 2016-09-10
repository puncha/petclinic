import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {OwnersComponent} from "./owners.component";
import {OwnerListComponent} from "./owner-list.component";
import OwnerService from "./owner.service";
import {ownersRouting} from "./owners.routing";

@NgModule({
  imports: [CommonModule, FormsModule, HttpModule, NgbModule, ownersRouting],
  providers: [OwnerService],
  declarations: [
    OwnersComponent,
    OwnerListComponent,
  ],

})
export class OwnersModule {
}