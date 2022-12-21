import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_services/auth.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-search-books',
  templateUrl: './search-books.component.html',
  styleUrls: ['./search-books.component.css']
})
export class SearchBooksComponent implements OnInit {

  constructor(private userService: UserService, private tokenStorageService: TokenStorageService) { }
  
  isLoggedIn = false;
  readerEmail : string = "";
  readerId : number = 0;
  users :any = [];
  content?: string[];
  books :any = [];
  
  search = {
    title : "",
    author : "",
    publisher : "",
    releasedDate : ""
  }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.readerEmail = user.email;
      this.readerId = user.id;
    }
    
  }

  searchBook(){
    console.log(this.search.title);
    console.log(this.search.author);
    console.log(this.search.publisher);
    console.log(this.search.releasedDate);

    // sessionStorage.setItem("searchQuery",'searchBook?title='+this.search.title+'&author='+this.search.author+'&publishedDate='+this.search.releasedDate+'&publisher='+this.search.publisher);
    sessionStorage.setItem("searchQuery",'search-book?title='+this.search.title+'&author='+this.search.author+'&publishedDate='+this.search.releasedDate+'&publisher='+this.search.publisher);
  }

}
