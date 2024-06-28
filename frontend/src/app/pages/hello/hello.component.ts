import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-hello',
  standalone: true,
  imports: [],
  templateUrl: './hello.component.html',
})
export class HelloComponent implements OnInit {
  message: string = '';

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.apiService.getHello().subscribe({
      next: (data) => {
        this.message = data.message;
      },
      error: (error) => {
        this.message = 'There was an error';
        console.log(error);
      },
    });
  }
}
