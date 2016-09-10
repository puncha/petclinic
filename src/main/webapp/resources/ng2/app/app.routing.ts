import {Routes, RouterModule} from "@angular/router";
import {ModuleWithProviders} from "@angular/core";
import {PetListComponent} from "./pets/pet-list.component";

const appRoutes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: '/welcome'},
  {path: 'pets', component: PetListComponent},
];

export const appRoutingProviders: any[] = [];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes, {useHash: true});