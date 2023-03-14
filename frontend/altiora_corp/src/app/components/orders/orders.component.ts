import {Component, OnInit, ViewChild} from '@angular/core';
import {Orders, OrdersDetail} from "../../models/Orders";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Customers} from "../../models/Customers";
import {Products} from "../../models/Products";
import {MatSelectionList} from '@angular/material/list';
import {format} from 'date-fns';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {


  @ViewChild('closeModal') modalOrder: any;
  @ViewChild(MatSelectionList) selectionList: MatSelectionList;

  currentDate = new Date();
  dateStr = format(this.currentDate, 'yyyy-MM-dd');

  orders: Orders[];
  order: Orders;

  ordersDetails: OrdersDetail[] = [];

  customers: Customers[] = [];
  customer: Customers;

  products: Products[] = [];
  product: Products;

  globalUri: string = "";
  regFormOrder: FormGroup;
  regFormDetailOrder: FormGroup;

  selectedOption: string;

  constructor(private _http: HttpClient, private formBuilder: FormBuilder) {
    this.regFormDetailOrder = this.formBuilder.group(
      {
        idDetails: 0,
        customerDetails: [],
        productDetails: [],
        dateDetails: []
      }
    );
  }


  ngOnInit() {

    this.getCustomers();
    this.getProducts();
    this.getOrders();

  }

  get form() {
    return this.regFormOrder.controls;
  }

  getOrders() {
    this.getOrdersApi().subscribe(response => {
      this.orders = response;
    });
  }

  getOrdersApi(): Observable<any> {
    this.globalUri = "http://localhost:8888/altioracorp/orders";
    return this._http.get<any>(this.globalUri, {});
  }

  getCustomers() {
    this.getCustomersApi().subscribe(response => {
      this.customers = response;
    });
  }

  getCustomersApi(): Observable<any> {
    this.globalUri = "http://localhost:8888/altioracorp/customers";
    return this._http.get<any>(this.globalUri, {});
  }

  getProducts() {
    this.getProductssApi().subscribe(response => {
      this.products = response;
    });
  }

  getProductssApi(): Observable<any> {
    this.globalUri = "http://localhost:8888/altioracorp/products";
    return this._http.get<any>(this.globalUri, {});
  }

  itemSelected(customers_: Customers) {
    this.customer = customers_;
    console.log(customers_);
  }


  prepareOrders(list: MatSelectionList) {
    let listMultiple = this.selectionList.selectedOptions.selected.map(item => item.value)
    if (listMultiple.length > 0) {
      console.log(this.selectedOption)
      this.regFormOrder = this.formBuilder.group(
        {
          id: 0,
          customer: [this.customer, Validators.required],
          date: [this.dateStr, Validators.required],
          state: 'A',
        }
      );

      this.order = {
        id: 0,
        customer: this.customer,
        date: this.dateStr,
        state: 'A',
        ordersDetails: []
      }

      for (let i = 0; i < listMultiple.length; i++) {
        this.ordersDetails.push(
          {
            id: 0,
            orders: this.order,
            products: listMultiple[i]
          }
        );

      }

      // @ts-ignore
      Swal.fire({
        title: '¿Desea finalizar la compra?',
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, finalizar!'
      }).then((result: any) => {
        if (result.isConfirmed) {
          this.saveOrderApi(this.order, this.ordersDetails).subscribe(res => {
            this.getOrders();
            this.modalOrder.nativeElement.click();
          });
        }
      })
    } else {
      console.log("No se ha seleccionado productos")
    }

    this.regFormOrder.reset();
    list.deselectAll();
    this.selectedOption = '----';
  }

  saveOrderApi(order_: Orders, orderDetail_: OrdersDetail[]): Observable<any> {
    this.globalUri = "http://localhost:8888/altioracorp/orders/insertorders";
    var headers = new HttpHeaders()
      .set('Access-Control-Allow-Origin', '*')
      .set('token', 'Security sesión');
    let jsonOrder = {
      'Order': order_,
      'OrdersDetails': orderDetail_
    }
    console.log(jsonOrder);
    return this._http.post(this.globalUri, JSON.stringify(jsonOrder), {headers: headers});
  }

  deleteOrder(orders_: Orders) {
    // @ts-ignore
    // @ts-ignore
    Swal.fire({
      title: 'Seguro de eliminar la orden número (' + orders_.id + ')?',
      text: "Esta accion no se puede revertir!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, Eliminar!'
    }).then((result: any) => {
      if (result.isConfirmed) {
        // @ts-ignore
        this.order = orders_;
        this.order.state = 'I';
        this.updateOrderApi(this.order).subscribe(response => {
          if (response.status === 2) {
            this.getOrders();
            this.modalOrder.nativeElement.click();
          }
        });
      }
    })
  }

  updateOrderApi(orders_: Orders): Observable<any> {
    this.globalUri = "http://localhost:8888/altioracorp/orders/inactiveorders";
    var headers = new HttpHeaders()
      .set('Access-Control-Allow-Origin', '*')
      .set('token', 'Security sesión');
    let jsonDeleteOrder = {orderid: orders_.id, orderdate: orders_.date, customerid: orders_.customer.id};
    return this._http.put(this.globalUri, JSON.stringify(jsonDeleteOrder), {headers: headers});
  }

  chargeDetails(order_: Orders) {
    console.log(order_);
    let allProducts = "";
    for (let i = 0; i < order_.ordersDetails.length; i++) {
      allProducts += order_.ordersDetails[i].products.code + '-' + order_.ordersDetails[i].products.name + ' / ';
    }

    this.regFormDetailOrder = this.formBuilder.group(
      {
        idDetails: order_.id,
        customerDetails: order_.customer.name + ' ' + order_.customer.lastname,
        productDetails: allProducts.slice(0, allProducts.length - 2),
        dateDetails: order_.date
      }
    );
  }

}
