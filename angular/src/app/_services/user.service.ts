import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const API_URL = 'http://localhost:8080/digitalbooks/';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) { }

  // getPublicContent(): Observable<any> {
  //   return this.http.get(API_URL + 'all', { responseType: 'text' });
  // }

  getAllBooks(authorId : number): Observable<any> {
    return this.http.get(API_URL + 'author/'+authorId, { responseType: 'json' });
  }
  
  blockOrUnblockBook(authorId : number,bookId : number, block : string){
    return this.http.put(API_URL + 'author/'+authorId+'/books/'+bookId+"/"+block, { responseType: 'json' });
  }

  //readers/{emailId}/books
  getSubscribedBooks(readerEmail : string): Observable<any> {
    return this.http.get(API_URL + 'readers/'+readerEmail+"/books", { responseType: 'json' });
  }

  //readers/{email-id}/books/{subscription-id}/cancel-subscription
  unSbscriveBook(readerEmail : string, subscriptionId : string){
    return this.http.put(API_URL + 'readers/'+readerEmail+'/books/'+subscriptionId+"/cancel-subscription", { responseType: 'json' });
  }

  //readers/{emailId}/books/{subscription-id}/read
  getBookContent(readerEmail : string, subscriptionId : string){
    return this.http.get(API_URL + 'readers/'+readerEmail+'/books/'+subscriptionId+"/read", { responseType: 'json' });
  }

  // getModeratorBoard(): Observable<any> {
  //   return this.http.get(API_URL + 'mod', { responseType: 'text' });
  // }

  // getAdminBoard(): Observable<any> {
  //   return this.http.get(API_URL + 'admin', { responseType: 'text' });
  // }
}
