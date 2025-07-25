import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Globals} from "./globals";
import {AlignmentService} from "./alignment-service/alignment.service";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

    constructor(private alignService:AlignmentService,  public globals: Globals) {
    }

    ngOnInit(): void {
    }


}


