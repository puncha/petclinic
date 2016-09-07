import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {AppComponent} from "./app.component";
import {WelcomeComponent} from "./welcome.component";
import {NavComponent} from "./nav.component";
import {FooterComponent} from "./footer.component";

@NgModule({
  imports: [BrowserModule, NgbModule],
  declarations: [AppComponent, WelcomeComponent, NavComponent, FooterComponent],
  bootstrap: [AppComponent]
})
export class AppModule {
}