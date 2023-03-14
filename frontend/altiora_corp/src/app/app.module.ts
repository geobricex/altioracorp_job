import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CustomersComponent} from './components/customers/customers.component';
import {OrdersComponent} from './components/orders/orders.component';
import {ProductsComponent} from './components/products/products.component';
import {MenuComponent} from './components/menu/menu.component';

import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgMultiSelectDropDownModule} from 'ng-multiselect-dropdown';
import {MatListModule} from '@angular/material/list';
import {MatSelectModule} from '@angular/material/select';
import {MatInputModule} from "@angular/material/input";
import {MatCheckboxModule} from "@angular/material/checkbox";


@NgModule({
  declarations: [
    AppComponent,
    CustomersComponent,
    OrdersComponent,
    ProductsComponent,
    MenuComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        ReactiveFormsModule,
        NgMultiSelectDropDownModule.forRoot(),
        MatListModule,
        MatSelectModule,
        FormsModule,
        MatInputModule,
        MatCheckboxModule


    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
