import {Routes, RouterModule} from "@angular/router";
import {ModuleWithProviders} from "@angular/core";

const appRoutes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: '/welcome'},
];

export const appRoutingProviders: any[] = [];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes, {useHash: true});