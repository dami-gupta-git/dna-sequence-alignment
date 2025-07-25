import {Component, OnInit} from '@angular/core';
import {Globals} from "../globals";
import {AlignmentService} from "../alignment-service/alignment.service";
import {LocalstorageService} from "../localstorage-service/localstorage.service";

/**
 * Component for result list pane
 */
@Component({
  selector: 'app-result-list',
  templateUrl: './result-list.component.html',
  styleUrls: ['./result-list.component.css']
})
export class ResultListComponent implements OnInit{

    constructor(public globals: Globals, private alignService:AlignmentService, private storageService: LocalstorageService) {

    }

    clearResults() {
        this.globals.matches = [];
        localStorage.setItem('matches','');
        localStorage.setItem('pending','');

    }

    ngOnInit(): void {

        this.globals.matches=[];

        // If there are any existing matches, display them
        let str = localStorage.getItem('matches');
        if (str && str.length > 0) {
            this.globals.matches = JSON.parse(str);
        }


        // If there are pending requests, submit them
        let pendingArr = this.storageService.getItemAsArr('pending')
        if(pendingArr !== null){
            for (let index = 0; index < pendingArr.length; index++) {
                this.alignService.submitSequence(pendingArr[index]);
                this.storageService.removeItemFromArr('pending',pendingArr[index]);
            }
        }

    }
}

