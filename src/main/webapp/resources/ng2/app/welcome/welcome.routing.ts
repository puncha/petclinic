import {Routes, RouterModule} from "@angular/router";
import {ModuleWithProviders} from "@angular/core";
import {WelcomeComponent} from "./welcome.component";

const appRoutes: Routes = [
  {path: 'welcome', component: WelcomeComponent},
];

export const welcomeRouting: ModuleWithProviders = RouterModule.forChild(appRoutes);