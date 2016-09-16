import {Routes, RouterModule} from "@angular/router";
import {ModuleWithProviders} from "@angular/core";
import {PetListComponent} from "./pet-list.component";

const appRoutes: Routes = [
  {path: 'pets', component: PetListComponent},
];

export const petsRouting: ModuleWithProviders = RouterModule.forChild(appRoutes);