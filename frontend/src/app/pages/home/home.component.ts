import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Address, Errors } from '../../types/models';
import { Store, select } from '@ngrx/store';
import { AddressActions } from '../../store/addresses.actions';
import { AppStateInterface } from '../../types/appstate.interface';
import { Observable } from 'rxjs';
import { LoadStatus } from '../../types/util';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './home.component.html',
})
export class HomeComponent implements OnInit {
  addresses$: Observable<Address[]>;
  addressStatus$: Observable<LoadStatus>;
  addressErrors$: Observable<Errors>;

  username: string = '';
  error: string = '';

  constructor(private store: Store<AppStateInterface>) {
    this.addresses$ = this.store.pipe(
      select((state) => state.addresses.addresses)
    );
    this.addressStatus$ = this.store.pipe(
      select((state) => state.addresses.status)
    );
    this.addressErrors$ = this.store.pipe(
      select((state) => state.addresses.errors)
    );

    this.addressStatus$.subscribe((status) => {
      if (status === LoadStatus.SUCCESS) {
        this.username = '';
      }
    });
  }

  ngOnInit(): void {
    this.store.dispatch(AddressActions.getAllAddresses());
  }

  handleSubmit(): void {
    const username = this.username.trim();
    const server = 'dsomorov.xyz';
    this.error = '';

    if (username.length < 3 || username.length > 30) {
      this.error = 'Username must be between 3 and 64 characters';
    } else if (username.match(/[^a-zA-Z0-9._\-\+]/)?.length ?? 0 > 0) {
      this.error = 'Characters must be alphanumeric or . _ + -';
    }

    if (!this.error) {
      this.store.dispatch(
        AddressActions.createAddress({ address: { username, server } })
      );
    }
  }
}
