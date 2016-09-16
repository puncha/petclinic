import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {routing, appRoutingProviders} from "./app.routing";
import {AppComponent} from "./app.component";
import {OwnersModule} from "./owners/owners.module";
import {WelcomeModule} from "./welcome/welcome.module";
import {PetModule} from "./pets/pets.module";
import {FooterModule} from "./footer/footer.module";
import {NavModule} from "./nav/nav.module";

@NgModule({
  imports: [
    BrowserModule,
    routing,
    NavModule, FooterModule, WelcomeModule, OwnersModule, PetModule
  ],
  providers: [appRoutingProviders],
  bootstrap: [AppComponent],
  declarations: [AppComponent]
})
export class AppModule {
}