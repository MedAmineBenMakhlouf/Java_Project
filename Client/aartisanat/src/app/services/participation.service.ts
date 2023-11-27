import { map, switchMap } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from "@angular/core";
import { Participation } from "../models/participation.model";

@Injectable({
    providedIn: 'root'
})

export class ParticipationsService {
    private apiUrl = 'http://localhost:8080/api/participation';
    constructor(private http: HttpClient) { }

    getParticipation(id: any) {
        return this.http.get(`${this.apiUrl}/${id}/participate`, { withCredentials: true });
    }

    saveBid(amount:any,id: any) {
        console.log(amount);
        
        return this.http.post(`${this.apiUrl}/${id}/addAuction`,amount, { withCredentials: true });
    }
}