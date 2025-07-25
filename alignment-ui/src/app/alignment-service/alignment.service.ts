import {Injectable} from '@angular/core';
import {Match} from "../match";
import {HttpClient} from '@angular/common/http';
import {Globals} from "../globals";
import {LocalstorageService} from "../localstorage-service/localstorage.service";



/**
 * Service to make REST queries to backend
 */
@Injectable({
    providedIn: 'root'
})
export class AlignmentService {
    allMatches: any;
    alignUrl = 'http://localhost:8080/alignAsync';
    matchesUrl = 'http://localhost:8080/allMatches';

    constructor(private http: HttpClient,  public globals: Globals, private storageService: LocalstorageService) {
    }

    /**
     * Submit query sequence for matching
     * @param seq The sequence to be submitted
     */
    submitSequence(seq) {
        let seqObj = {sequence: seq};
        let headers = {'Access-Control-Allow-Origin': '*'}

        // Request is stored in localstorage in case there is a restart before the result arrives at the UI
        this.storageService.addItemToArr('pending', seq);

        // POST call
        this.http
            .post(this.alignUrl, seqObj, {'headers': headers})
            .subscribe(data => {
                let result = this.createMatchObject(data);
                console.log(this.globals.matches)
                console.log(result)
                this.globals.matches.unshift(result);

                //Matches are stored in localStorage so that they are maintained across restart
                localStorage.setItem('matches', JSON.stringify(this.globals.matches));
                this.storageService.removeItemFromArr('pending', seq);
            });
    }

    /**
     * Create Match object from data
     * @param data Data containing Match information
     */
    createMatchObject(data) {
        let cur = new Match();
        if (data !== null) {
            cur.position = data.position;
            cur.proteinId = data.proteinId;
            cur.query = (data.query);
            cur.assembly = data.assembly;
        }
        return cur;
    }
}
