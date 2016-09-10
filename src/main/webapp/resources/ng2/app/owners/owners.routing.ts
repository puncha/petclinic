import {Routes, RouterModule} from "@angular/router";
import {ModuleWithProviders} from "@angular/core";
import {OwnersComponent} from "./owners.component";

const appRoutes: Routes = [
  {path: 'owners', component: OwnersComponent}
];

export const ownersRouting: ModuleWithProviders = RouterModule.forChild(appRoutes);