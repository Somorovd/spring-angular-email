import { Address } from './address.interface';

export interface Email {
  id: number;
  chainId: number;
  isDraft: boolean;
  subject: string;
  body: string;
  date: Date;
  sender: Address;
  recipients: Address[];
}
