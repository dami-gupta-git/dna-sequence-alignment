import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalstorageService {

  constructor() { }

  getItemAsStr(key){
    return localStorage.getItem(key);
  }

  getItemAsArr(key){
    let str = localStorage.getItem(key);
    if (str === null || str.length ==0 ){
      return null;
    }
    let arr = JSON.parse(str);
    return arr;
  }

  // This item is meant to be an array.
  addItemToArr(key, item){
      let cur = [];
      let str = localStorage.getItem(key);
      if (str != null && str.length > 0 ){
        cur = JSON.parse(str);
      }
      cur.push(item);
      localStorage.setItem(key, JSON.stringify(cur));
  }

  removeItemFromArr(key, item){
    let cur = [];
    let str = localStorage.getItem(key);
    if (str != null && str.length > 0 ){
      cur = JSON.parse(str);
    }
    for (let index = 0; index < cur.length; index++) {
      if(cur[index] === item){
        cur.splice(index, 1);
      }
    }
    localStorage.setItem(key, JSON.stringify(cur));
  }


}
