import { createActionGroup, emptyProps, props } from '@ngrx/store';
import { Address, Errors } from '../types/models';

export const AddressActions = createActionGroup({
  source: 'Addresses',
  events: {
    // ------------------------------------------------
    'Get All Addresses': emptyProps(),
    'Get All Addresses Success': props<{ addresses: Address[] }>(),
    // ------------------------------------------------
    'Create Address': props<{ address: Address }>(),
    'Create Address Success': props<{ address: Address }>(),
    // ------------------------------------------------
    'Request Failed': props<{ errors: Errors }>(),
    // ------------------------------------------------
  },
});
