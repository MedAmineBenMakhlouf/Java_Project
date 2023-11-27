import { map, switchMap } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from "@angular/core";
import { Wallet } from "../models/wallet.model";

@Injectable({
    providedIn: 'root'
})

export class WalletsService {

    private apiUrl = 'http://localhost:8080/api/wallet';
    constructor(private http: HttpClient) { }
    
    configWallet(amount:any): Observable<any> {
        return this.http.put(`${this.apiUrl}/update`,amount, { withCredentials: true });
    }

    withdraw(amount:any): Observable<any> {
        return this.http.put(`${this.apiUrl}/withdraw`,amount, { withCredentials: true });
    }
}