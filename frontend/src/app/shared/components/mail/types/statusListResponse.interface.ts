import { Status } from '../../../types/status.interface';

export interface StatusListResponseInterface {
  statuses: Status[];
  count: number;
}
