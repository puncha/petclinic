import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {AppComponent} from "./app.component";
import {WelcomeComponent} from "./welcome.component";
import {NavComponent} from "./nav.component";
import {FooterComponent} from "./footer.component";
import {routing, appRoutingProviders} from "./app.routing";
import {PetListComponent} from "./pet-list.component";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {OwnersModule} from "./owners/owners.module";

@NgModule({
  imports: [BrowserModule, FormsModule, HttpModule, NgbModule, routing, OwnersModule],
  providers: [appRoutingProviders],
  bootstrap: [AppComponent],
  declarations: [
    AppComponent,
    WelcomeComponent,
    NavComponent,
    FooterComponent,
    PetListComponent
  ],

})
export class AppModule {
}