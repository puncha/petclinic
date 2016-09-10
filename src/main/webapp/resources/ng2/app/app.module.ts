import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {AppComponent} from "./app.component";
import {NavComponent} from "./nav.component";
import {FooterComponent} from "./footer.component";
import {routing, appRoutingProviders} from "./app.routing";
import {OwnersModule} from "./owners/owners.module";
import {WelcomeModule} from "./welcome/welcome.module";
import {PetModule} from "./pets/pets.module";

@NgModule({
  imports: [
    BrowserModule, FormsModule, HttpModule, NgbModule,
    routing, WelcomeModule, OwnersModule, PetModule
  ],
  providers: [appRoutingProviders],
  bootstrap: [AppComponent],
  declarations: [
    AppComponent,
    NavComponent,
    FooterComponent
  ],

})
export class AppModule {
}