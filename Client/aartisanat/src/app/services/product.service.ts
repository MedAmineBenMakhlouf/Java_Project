import { map, switchMap } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from "@angular/core";
import { Product } from "../models/product.model";

@Injectable({
    providedIn: 'root'
})

export class ProductsService {
    

    private apiUrl = 'http://localhost:8080/api/product';
    constructor(private http: HttpClient) { }


    registerProduct(formData: FormData): Observable<any> {
        return this.http.post(`${this.apiUrl}/add`, formData, { withCredentials: true });
    }
    getMyList(): Observable<any> {
        return this.http.get(`${this.apiUrl}/showAllMine`, { withCredentials: true });
    }

    getOneProduct(id: any): Observable<any> {
        return this.http.get(`${this.apiUrl}/${id}/findOne`, { withCredentials: true })
    }
    buyProduct(productId: any): Observable<any> {
        const userId = sessionStorage.getItem("user_id");
        return this.http.post(`${this.apiUrl}/${productId}/${userId}/buy`, { withCredentials: true })
    }

    sellProduct(productId:any):Observable<any>
    {
        return this.http.post(`${this.apiUrl}/sell/${productId}`, { withCredentials: true })
    }
    
    getMyProduct(id: any): Observable<any> {
        return this.http.get(`${this.apiUrl}/${id}/show`, { withCredentials: true })
    }

    getByCategory(category:any):Observable<any>{
        return  this.http.get(`${this.apiUrl}/${category}`, { withCredentials: true })
    }
    getProduct(id: any): Observable<any> {
        return this.http.get(`${this.apiUrl}/${id}/findOne`, { withCredentials: true })
    }
    getselledProducts(): Observable<any> {
        return this.http.get(`${this.apiUrl}/selled`, { withCredentials: true })
    }
    getboughtProducts(): Observable<any> {
        return this.http.get(`${this.apiUrl}/bought`, { withCredentials: true })
    }
    deleteProduct(id:any):Observable<any>{
        return this.http.delete(`${this.apiUrl}/${id}/remove`,{withCredentials :true});
    }
}