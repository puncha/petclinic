import {NgModule} from "@angular/core";
import {WelcomeComponent} from "./welcome.component";
import {welcomeRouting} from "./welcome.routing";
import {CommonModule} from "@angular/common";

@NgModule({
  imports: [CommonModule, welcomeRouting],
  declarations: [
    WelcomeComponent
  ],

})
export class WelcomeModule {
}