import {Customers} from "./Customers";

export interface Orders {
  id?: number;
  customer: Customers;
  date?: string;
  state?: string;
  ordersDetails: OrdersDetail[];
}

export interface OrdersDetail {
  id: number;
  orders: Orders
  products: Products;
}

export interface Products {
  id: number;
  code: number;
  name: string;
  unitPrice: number;
  state: string;
}


