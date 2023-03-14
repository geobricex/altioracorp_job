import {Component, OnInit, ViewChild} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Products} from "../../models/Products";
import {Customers} from "../../models/Customers";

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  @ViewChild('closeModal') modalProduct: any;

  products: Products[] = [];
  product: Products;

  globalUri: string = "";
  regFormProduct: FormGroup;
  flagnew: number = 1;

  cantProduct: number = 0;

  constructor(private _http: HttpClient, private formBuilder: FormBuilder) {
    this.regFormProduct = this.formBuilder.group(
      {
        id: [0],
        code: [0, Validators.required],
        name: ["", Validators.required],
        unitPrice: [0, Validators.required],
        cant: [0, Validators.required],
        state: 'A',
      }
    );
  }

  ngOnInit(): void {
    this.getProducts();
  }

  get form() {
    return this.regFormProduct.controls;
  }

  getProducts() {
    this.getProductssApi().subscribe(response => {
      console.log(response);
      this.products = response;
    });
  }

  getProductssApi(): Observable<any> {
    this.globalUri = "http://localhost:8888/altioracorp/products";
    return this._http.get<any>(this.globalUri, {});
  }

  saveProduct() {

    if (this.regFormProduct.invalid) {
      console.log("FORMULARIO INVALIDO");
      return;
    }

    this.product = {
      id: this.form['id'].value,
      code: this.form['code'].value,
      name: this.form['name'].value,
      unitPrice: this.form['unitPrice'].value,
      cant: this.form['cant'].value,
      state: 'A'
    }

    if (this.flagnew == 1) {
      console.log("saveProduct()");
      console.log(this.product);
      this.saveProductApi(this.product).subscribe(response => {
        console.log(response);
        this.getProducts();

      });
    } else if (this.flagnew == 2) {
      console.log("updateProduct()");
      this.updateProductApi(this.product).subscribe(response => {
        console.log(response);
        this.getProducts();

      });
    }
    this.modalProduct.nativeElement.click();
    this.regFormProduct.reset();
  }

  saveProductApi(products_: Products): Observable<any> {
    this.globalUri = "http://localhost:8888/altioracorp/products/insertproducts";
    var headers = new HttpHeaders()
      .set('Access-Control-Allow-Origin', '*')
      .set('token', 'Security_sesión');
    return this._http.post(this.globalUri, products_, {headers: headers});
  }

  deleteProduct(products_: Products) {
    // @ts-ignore
    // @ts-ignore
    Swal.fire({
      title: 'Seguro de eliminar el producto (' + products_.name + ')?',
      text: "Esta accion no se puede revertir!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, Eliminar!'
    }).then((result: any) => {
      if (result.isConfirmed) {
        // @ts-ignore
        this.product = products_;
        this.product.state = 'I';

        this.updateProductApi(this.product).subscribe(response => {
          console.log(response);
          if (response.status === 2) {
            this.getProducts();
            this.modalProduct.nativeElement.click();
          }
        });
      }
    })
  }

  updateProduct(products_: Products) {
    console.log(products_);
    this.flagnew = 2;
    this.regFormProduct = this.formBuilder.group(
      {
        id: products_.id,
        code: products_.code,
        name: products_.name,
        cant: products_.cant,
        unitPrice: products_.unitPrice
      }
    );
  }

  updateProductApi(products_: Products): Observable<any> {
    this.globalUri = "http://localhost:8888/altioracorp/products/updateproducts";
    var headers = new HttpHeaders()
      .set('Access-Control-Allow-Origin', '*')
      .set('token', 'Security sesión');
    return this._http.put(this.globalUri, products_, {headers: headers});
  }
}
