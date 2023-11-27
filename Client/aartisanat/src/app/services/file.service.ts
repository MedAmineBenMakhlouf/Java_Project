import { map, switchMap } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { HttpClient} from '@angular/common/http';
import { Injectable } from "@angular/core";
import { File } from "../models/file.model";

@Injectable({
    providedIn: 'root'
})

export class FilesService {}