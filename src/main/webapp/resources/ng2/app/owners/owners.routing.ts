import {Routes, RouterModule} from "@angular/router";
import {ModuleWithProviders} from "@angular/core";
import {OwnersComponent} from "./owners.component";
import {OwnerFormComponent} from "./owner-form/owner-form.component";
import {CreateOwnerButtonComponent} from "./create-owner-button/create-owner-button.component";

const appRoutes: Routes = [
  {
    path: 'owners', component: OwnersComponent,
    children: [
      {path: '', component: CreateOwnerButtonComponent},
      {path: 'create', component: OwnerFormComponent}
    ]
  },
];

export const ownersRouting: ModuleWithProviders = RouterModule.forChild(appRoutes);