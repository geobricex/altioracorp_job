import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MenuComponent} from "./components/menu/menu.component";
import {CustomersComponent} from "./components/customers/customers.component";
import {ProductsComponent} from "./components/products/products.component";
import {OrdersComponent} from "./components/orders/orders.component";

const routes: Routes = [
  {path: "home", component: MenuComponent},
  {path: "customers", component: CustomersComponent},
  {path: "products", component: ProductsComponent},
  {path: "orders", component: OrdersComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
