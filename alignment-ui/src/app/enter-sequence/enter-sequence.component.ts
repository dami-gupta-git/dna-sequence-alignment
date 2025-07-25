import { Component } from '@angular/core';
import { AlignmentService} from "../alignment-service/alignment.service";

/**
 * Component for sequence entry pane
 */
@Component({
  selector: 'app-enter-sequence',
  templateUrl: './enter-sequence.component.html',
  styleUrls: ['./enter-sequence.component.css']
})
export class EnterSequenceComponent {

  constructor(private alignService: AlignmentService) {
  }

  sequence:string;

  /**
   * Submit user-entered sequence to backend for alignment
   * @param seq The sequence
   */
  submitSequence(seq) {
    //Remove spaces, new lines
    seq = seq.replace(/(\r\n|\n|\r|\s)/gm,"");

    // Check for characters which are not standard DNA nts.
    var res  = seq.toUpperCase().match('^[ATGC]+$');

    if(res !== null) {
      // Submit sequence to service
      this.alignService.submitSequence(seq);
      this.clearInput();
    }
   else
     window.alert('Your submission can only contain characters A,T,G,C');
  }


  clearInput() {
    this.sequence="";
  }
}

