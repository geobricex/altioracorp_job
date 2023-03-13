import {Component, OnInit, ViewChild} from '@angular/core';
import {Customers} from "../../models/Customers";
import {FormGroup, FormControl, Validators, ReactiveFormsModule, FormsModule, FormBuilder} from '@angular/forms';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css']
})

export class CustomersComponent implements OnInit {

  @ViewChild('closeModal') modalCustomer: any;

  customers: Customers[] = [];
  customer: Customers = {};
  flagnew: number = 1;
  globalUri: string = "";
  regFormCustomer: FormGroup;

  constructor(private _http: HttpClient, private formBuilder: FormBuilder) {
    this.regFormCustomer = this.formBuilder.group(
      {
        id: [0],
        dni: ["", Validators.required],
        name: ["", Validators.required],
        lastname: ["", Validators.required]
      }
    );
  }

  ngOnInit(): void {
    this.getCustomers();
  }

  get form() {
    return this.regFormCustomer.controls;
  }

  getCustomers() {
    this.getCustomersApi().subscribe(response => {
      console.log(response);
      this.customers = response;
    });
  }

  getCustomersApi(): Observable<any> {
    this.globalUri = "http://localhost:8888/altioracorp/customers";
    return this._http.get<any>(this.globalUri, {});
  }

  saveCustomer() {

    if (this.regFormCustomer.invalid) {
      console.log("FORMULARIO INVALIDO");
      return;
    }
    this.customer = {
      id: this.form['id'].value,
      dni: this.form['dni'].value,
      name: this.form['name'].value,
      lastname: this.form['lastname'].value,
      state: 'A'
    }

    if (this.flagnew == 1) {
      console.log("saveCustomers()");

      this.saveCustomerApi(this.customer).subscribe(response => {
        console.log(response);
        this.getCustomers();
      });
    } else if (this.flagnew == 2) {
      console.log("updateCustomers()");

      this.updateCustomerApi(this.customer).subscribe(response => {
        console.log(response);
        this.getCustomers();
      });
    }

    this.modalCustomer.nativeElement.click();
    this.flagnew = 1;
    this.regFormCustomer.reset();
  }

  saveCustomerApi(customers_: Customers): Observable<any> {
    this.globalUri = "http://localhost:8888/altioracorp/customers/insertcustomer";
    var headers = new HttpHeaders()
      .set('Access-Control-Allow-Origin', '*')
      .set('token', 'Security_sesión');
    return this._http.post(this.globalUri, customers_, {headers: headers});
  }

  deleteCustomer(customers_: Customers) {
    // @ts-ignore
    // @ts-ignore
    Swal.fire({
      title: 'Seguro de eliminar al cliente (' + customers_.name + ' ' + customers_.lastname + ')?',
      text: "Esta accion no se puede revertir!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, Eliminar!'
    }).then((result: any) => {
      if (result.isConfirmed) {
        // @ts-ignore
        this.customer = customers_;
        this.customer.state = 'I';

        this.updateCustomerApi(this.customer).subscribe(response => {
          console.log(response);
          if (response.status === 2) {
            this.getCustomers();
            this.modalCustomer.nativeElement.click();
          }
        });
      }
    })
  }

  updateCustomer(customers_: Customers) {
    console.log(customers_);
    this.flagnew = 2;
    this.regFormCustomer = this.formBuilder.group(
      {
        id: customers_.id,
        dni: customers_.dni,
        name: customers_.name,
        lastname: customers_.lastname
      }
    );
  }

  updateCustomerApi(customers_: Customers): Observable<any> {
    this.globalUri = "http://localhost:8888/altioracorp/customers/updatecustomer";
    console.log(customers_);
    var headers = new HttpHeaders()
      .set('Access-Control-Allow-Origin', '*')
      .set('token', 'Security sesión');
    return this._http.put(this.globalUri, customers_, {headers: headers});
  }
}
