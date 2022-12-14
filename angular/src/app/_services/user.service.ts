import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const API_URL = 'http://localhost:8081/digitalbooks/';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) { }

  // getPublicContent(): Observable<any> {
  //   return this.http.get(API_URL + 'all', { responseType: 'text' });
  // }

  //author/{author-id}/books
  createBook(image : File, create : any, authorId : number){
    const formdata : FormData = new FormData();
    formdata.append('image',image);
    formdata.append('title',create.title);
    formdata.append('category',create.category);
    formdata.append('price',create.price);
    formdata.append('publisher',create.publisher);
    formdata.append('active',create.active);
    formdata.append('content',create.content);

    return this.http.post(API_URL +'author/'+authorId+'/books',formdata, { responseType: 'json' });
  }

  //author/{author-id}/books/{book-id}
  updateBook(image : File, update : any, authorId : number, bookId:number){
    const formdata : FormData = new FormData();
    formdata.append('image',image);
    formdata.append('title',update.title);
    formdata.append('category',update.category);
    formdata.append('price',update.price);
    formdata.append('publisher',update.publisher);
    formdata.append('active',update.active);
    formdata.append('content',update.content);

    return this.http.put(API_URL +'author/'+authorId+'/books/'+bookId,formdata, { responseType: 'json' });
  }

  getAllBooks(authorId : number): Observable<any> {
    return this.http.get(API_URL + 'author/'+authorId, { responseType: 'json' });
  }

  getBook(bookId : any): Observable<any> {
    return this.http.get(API_URL + 'test/'+bookId, { responseType: 'json' });
  }
  
  blockOrUnblockBook(authorId : number,bookId : number, block : string){
    return this.http.put(API_URL + 'author/'+authorId+'/books/'+bookId+"/"+block, { responseType: 'json' });
  }

  //readers/{emailId}/books
  getSubscribedBooks(readerEmail : string): Observable<any> {
    return this.http.get(API_URL + 'readers/'+readerEmail+"/books", { responseType: 'json' });
  }

  //readers/{email-id}/books/{subscription-id}/cancel-subscription
  unSubscriveBook(readerEmail : string, subscriptionId : string){
    return this.http.put(API_URL + 'readers/'+readerEmail+'/books/'+subscriptionId+"/cancel-subscription", { responseType: 'json' });
  }

  ///{book-id}/subscribe
  subscribeBook(email : string, bookId : number){
    return this.http.post(API_URL +bookId+'/subscribe',{
      bookId,
      email
    }, { responseType: 'json' });
  }


  //readers/{emailId}/books/{subscription-id}/read
  getBookContent(readerEmail : string, subscriptionId : string){
    return this.http.get(API_URL + 'readers/'+readerEmail+'/books/'+subscriptionId+"/read", { responseType: 'json' });
  }

  //all/authors
  // getAllAuthors(): Observable<any> {
  //   return this.http.get(API_URL + 'all/authors', { responseType: 'json' });
  // }

  //'searchBook?title=&author=&publishedDate=&publisher='
  searchBook(searchQuery : any ):  Observable<any> {
    if(searchQuery === null || searchQuery === undefined)
      return this.http.get(API_URL + 'searchBook?title=&author=&publishedDate=&publisher=', { responseType: 'json' });
    return this.http.get(API_URL + searchQuery, { responseType: 'json' });
  }

  // getModeratorBoard(): Observable<any> {
  //   return this.http.get(API_URL + 'mod', { responseType: 'text' });
  // }

  // getAdminBoard(): Observable<any> {
  //   return this.http.get(API_URL + 'admin', { responseType: 'text' });
  // }
}
