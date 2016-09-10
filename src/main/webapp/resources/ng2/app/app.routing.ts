import {Routes, RouterModule} from "@angular/router";
import {WelcomeComponent} from "./welcome.component";
import {ModuleWithProviders} from "@angular/core";
import {PetListComponent} from "./pet-list.component";
import {OwnersComponent} from "./owners/owners.component";

const appRoutes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: '/welcome'},
  {path: 'welcome', component: WelcomeComponent},
  {path: 'owners', component: OwnersComponent},
  {path: 'pets', component: PetListComponent},
];

export const appRoutingProviders: any[] = [];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes, {useHash: true});