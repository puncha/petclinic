import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {PetListComponent} from "./pet-list.component";
import {petsRouting} from "./pets.routing";

@NgModule({
  imports: [CommonModule, petsRouting],
  declarations: [
    PetListComponent
  ],

})
export class PetModule {
}