import { Status } from '../../shared/types/status.interface';

export interface StatusListResponseInterface {
  statuses: Status[];
  count: number;
}
